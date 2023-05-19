package com.github.szilex94.edu.round_tracker.repository.tracking.repository;

import com.github.szilex94.edu.round_tracker.configuration.app.ArchivingConfiguration;
import com.github.szilex94.edu.round_tracker.repository.support.caliber.mongo.CaliberTypeDefinitionDao;
import com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionChangeBucketDao;
import com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionChangeLogDao;
import com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionChangeLogDao.ArchivingStatus;
import com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionSummaryDao;
import com.github.szilex94.edu.round_tracker.service.tracking.model.UnknownAmmunitionCodeException;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static com.github.szilex94.edu.round_tracker.repository.support.caliber.mongo.CaliberTypeDefinitionDao.FIELD_CALIBER_CODE;
import static com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionChangeLogDao.FIELD_RECORDED_AT;
import static com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionSummaryDao.ChangeEntry.FIELD_GRAND_TOTAL;
import static com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionSummaryDao.ChangeEntry.FIELD_LAST_RECORDED_CHANGE;
import static com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionSummaryDao.FIELD_ENTRIES;
import static com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionSummaryDao.FIELD_USER_ID;

public class CustomTrackingRepositoryImpl implements CustomTrackingRepository {

    private final int bucketSize;

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    private final ReactiveTransactionManager reactiveTransactionManager;

    public CustomTrackingRepositoryImpl(ReactiveMongoTemplate reactiveMongoTemplate,
                                        ReactiveTransactionManager reactiveTransactionManager,
                                        ArchivingConfiguration configuration) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.reactiveTransactionManager = reactiveTransactionManager;
        this.bucketSize = configuration.bucketSize();
    }

    @Override
    public Mono<AmmunitionSummaryDao> recordAmmunitionChange(AmmunitionChangeLogDao change) {

        final var userId = change.getUserId();
        final var ammunitionCode = change.getAmmunitionCode();

        TransactionalOperator transactionalOperator = TransactionalOperator.create(reactiveTransactionManager);

        Query ammunitionCodeQuery = Query.query(
                Criteria.where(FIELD_CALIBER_CODE).is(ammunitionCode));

        Query ammunitionSummaryQuery = Query.query(
                Criteria.where(FIELD_USER_ID).is(userId));

        UpdateDefinition summaryUpsert = new Update()
                .setOnInsert(FIELD_USER_ID, userId)
                .currentDate(FIELD_ENTRIES + "." + ammunitionCode + "." + FIELD_LAST_RECORDED_CHANGE)
                .inc(FIELD_ENTRIES + "." + ammunitionCode + "." + FIELD_GRAND_TOTAL, change.getAmount());

        return reactiveMongoTemplate.findOne(ammunitionCodeQuery, CaliberTypeDefinitionDao.class)
                .switchIfEmpty(Mono.error(UnknownAmmunitionCodeException::new)) // If the code is not defined raise an exception
                .then(reactiveMongoTemplate.insert(change))
                .then(reactiveMongoTemplate.upsert(ammunitionSummaryQuery, summaryUpsert, AmmunitionSummaryDao.class))
                .as(transactionalOperator::transactional)
                .then(reactiveMongoTemplate.findOne(ammunitionSummaryQuery, AmmunitionSummaryDao.class));
    }

    @Override
    public Mono<Long> markEntriesForArchiving(LocalDate cutoff) {

        Query beforeDate = Query.query(Criteria.where(FIELD_RECORDED_AT).lte(cutoff));

        UpdateDefinition markForArchiving = new Update()
                .set(AmmunitionChangeLogDao.FIELD_ARCHIVING_STATE, ArchivingStatus.MARKED_FOR_ARCHIVING);

        return reactiveMongoTemplate.update(AmmunitionChangeLogDao.class)
                .matching(beforeDate)
                .apply(markForArchiving)
                .all()
                .map(UpdateResult::getModifiedCount);
    }

    @Override
    public Flux<AmmunitionChangeLogDao> transferMarkedEntities() {

        TransactionalOperator transactionalOperator = TransactionalOperator.create(reactiveTransactionManager);

        Query query = Query.query(Criteria.where(AmmunitionChangeLogDao.FIELD_ARCHIVING_STATE).is(ArchivingStatus.MARKED_FOR_ARCHIVING))
                .with(Sort.by(Sort.Direction.ASC, FIELD_RECORDED_AT));


        return reactiveMongoTemplate.find(query, AmmunitionChangeLogDao.class)
                .flatMap(this::moveToLongTermStorageCollection)
                .flatMap(this::markAsArchived)
                .as(transactionalOperator::transactional);
    }

    private Mono<AmmunitionChangeLogDao> moveToLongTermStorageCollection(AmmunitionChangeLogDao markedForArchiving) {

        final var userId = markedForArchiving.getUserId();
        final var recordedAt = markedForArchiving.getRecordedAt();
        final var amount = markedForArchiving.getAmount();
        var simplifiedEntry = computeSimplifiedEntry(markedForArchiving);

        Query query = Query.query(Criteria.where(AmmunitionChangeBucketDao.FIELD_USER_ID).is(userId)
                        .and(AmmunitionChangeBucketDao.FIELD_AMMUNITION_CODE).is(markedForArchiving.getAmmunitionCode())
                        .and(AmmunitionChangeBucketDao.FIELD_ENTRY_COUNT).lt(this.bucketSize))
                .limit(1);//TODO look into special case where limit is raised and multiple buckets can match

        UpdateDefinition summaryUpsert = new Update()
                .setOnInsert(AmmunitionChangeBucketDao.FIELD_USER_ID, userId)
                .setOnInsert(AmmunitionChangeBucketDao.FIELD_OLDEST_ENTRY_TIME_STAMP, recordedAt)
                .set(AmmunitionChangeBucketDao.FIELD_LATEST_ENTRY_TIME_STAMP, recordedAt)
                .push(AmmunitionChangeBucketDao.FIELD_ENTRIES, simplifiedEntry)
                .inc(AmmunitionChangeBucketDao.FIELD_TOTAL_AMOUNT, amount)
                .inc(AmmunitionChangeBucketDao.FIELD_ENTRY_COUNT, 1);


        return reactiveMongoTemplate.upsert(query, summaryUpsert, AmmunitionChangeBucketDao.class)
                .onErrorResume(error -> Mono.error(ArchiveTransferFailedException.forEntity(markedForArchiving, error)))
                .thenReturn(markedForArchiving);
    }

    private AmmunitionChangeBucketDao.ArchivedAmmunitionChangeLog computeSimplifiedEntry(AmmunitionChangeLogDao markedForArchiving) {
        return new AmmunitionChangeBucketDao.ArchivedAmmunitionChangeLog(
                markedForArchiving.getId(),
                markedForArchiving.getRecordedAt(),
                markedForArchiving.getChangeType(),
                markedForArchiving.getAmount()
        );
    }

    private Mono<AmmunitionChangeLogDao> markAsArchived(AmmunitionChangeLogDao dao) {

        Query q = Query.query(Criteria.where(AmmunitionChangeLogDao.FIELD_ID).is(dao.getId()));
        UpdateDefinition ud = Update.update(AmmunitionChangeLogDao.FIELD_ARCHIVING_STATE, ArchivingStatus.ARCHIVED);

        return reactiveMongoTemplate.findAndModify(q, ud, AmmunitionChangeLogDao.class);
    }

}

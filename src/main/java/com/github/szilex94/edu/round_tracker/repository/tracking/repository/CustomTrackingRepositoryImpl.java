package com.github.szilex94.edu.round_tracker.repository.tracking.repository;

import com.github.szilex94.edu.round_tracker.repository.support.caliber.mongo.CaliberTypeDefinitionDao;
import com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionChangeLogDao;
import com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionSummaryDao;
import com.github.szilex94.edu.round_tracker.service.tracking.model.UnknownAmmunitionCodeException;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static com.github.szilex94.edu.round_tracker.repository.support.caliber.mongo.CaliberTypeDefinitionDao.FIELD_CALIBER_CODE;
import static com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionChangeLogDao.FIELD_RECORDED_AT;
import static com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionSummaryDao.ChangeEntry.*;
import static com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionSummaryDao.FIELD_ENTRIES;
import static com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionSummaryDao.FIELD_USER_ID;

@AllArgsConstructor
public class CustomTrackingRepositoryImpl implements CustomTrackingRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    private final ReactiveTransactionManager reactiveTransactionManager;


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
                .set(AmmunitionChangeLogDao.FIELD_MARKED_FOR_ARCHIVING, true);

        return reactiveMongoTemplate.update(AmmunitionChangeLogDao.class)
                .matching(beforeDate)
                .apply(markForArchiving)
                .all()
                .map(UpdateResult::getModifiedCount);
    }

    @Override
    public Mono<Void> transferMarkedEntities() {

        TransactionalOperator transactionalOperator = TransactionalOperator.create(reactiveTransactionManager);



        return null;
    }

}

package com.github.szilex94.edu.round_tracker.repository.tracking.repository;

import com.github.szilex94.edu.round_tracker.repository.support.caliber.mongo.CaliberTypeDefinitionDao;
import com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionChangeLogDao;
import com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionSummaryDao;
import com.github.szilex94.edu.round_tracker.service.tracking.model.UnknownAmmunitionCodeException;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

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
                Criteria.where(CaliberTypeDefinitionDao.FIELD_CALIBER_CODE).is(ammunitionCode));

        Query ammunitionSummaryQuery = Query.query(
                Criteria.where(AmmunitionSummaryDao.FIELD_USER_ID).is(userId));

        UpdateDefinition summaryUpsert = new Update()
                .setOnInsert(AmmunitionSummaryDao.FIELD_USER_ID, userId)
                .inc(AmmunitionSummaryDao.FIELD_CODE_TO_TOTAL + '.' + ammunitionCode, change.getAmount());

        return reactiveMongoTemplate.findOne(ammunitionCodeQuery, CaliberTypeDefinitionDao.class)
                .switchIfEmpty(Mono.error(UnknownAmmunitionCodeException::new)) // If the code is not defined raise an exception
                .then(reactiveMongoTemplate.save(change))
                .then(reactiveMongoTemplate.upsert(ammunitionSummaryQuery, summaryUpsert, AmmunitionSummaryDao.class))
                .as(transactionalOperator::transactional)
                .then(reactiveMongoTemplate.findOne(ammunitionSummaryQuery, AmmunitionSummaryDao.class));
    }

}

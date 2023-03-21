package com.github.szilex94.edu.round_tracker.repository.tracking.repository;

import com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionChangeLogDao;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@AllArgsConstructor
public class CustomTrackingRepositoryImpl implements CustomTrackingRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    private final ReactiveTransactionManager reactiveTransactionManager;


    @Override
    public Mono<Void> recordAmmunitionChange(AmmunitionChangeLogDao change) {

        TransactionalOperator transactionalOperator = TransactionalOperator.create(reactiveTransactionManager);

//TODO figure out bucketing strategy
        var lowerBound = OffsetDateTime.of(2023, 4, 1, 0, 0, 0, 0, ZoneOffset.UTC);
        var upperBound = OffsetDateTime.of(2023, 6, 30, 0, 0, 0, 0, ZoneOffset.UTC);
        var present = OffsetDateTime.now().plusMonths(1);
        CriteriaDefinition cd = Criteria.where("userId").is(change.getUserId())
                .and("startDate").lt(present)
                .and("endDate").gt(present);

        Query q = Query.query(cd);

        var bson = new Document();
        bson.put("changeType", change.getChangeType().name());
        bson.put("date", present);
        bson.put("amount", change.getAmount());
        bson.put("details", null);

        UpdateDefinition ud = new Update()
                .setOnInsert("userId", change.getUserId())
                .setOnInsert("startDate", lowerBound)
                .setOnInsert("endDate", upperBound)
                .push("entries", bson)
                .inc("total", change.getAmount());


        ud.inc("entryCount");

        CriteriaDefinition summaryCD = Criteria.where("userId").is(change.getUserId());

        Query summaryQuery = Query.query(summaryCD);

        UpdateDefinition summaryUD = new Update()
                .setOnInsert("userId", change.getUserId())
                .inc("nineMillimeter", change.getAmount());

        return reactiveMongoTemplate.upsert(q, ud, AmmunitionChangeBucketEntry.class)
                .then(reactiveMongoTemplate.upsert(summaryQuery, summaryUD, "user.tracking.summary"))
                .as(transactionalOperator::transactional)
                .then(reactiveMongoTemplate.findOne(summaryQuery, SummaryDao.class))
                .log()
                .then();

    }
}

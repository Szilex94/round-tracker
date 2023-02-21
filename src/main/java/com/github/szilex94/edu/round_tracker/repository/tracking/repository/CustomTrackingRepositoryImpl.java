package com.github.szilex94.edu.round_tracker.repository.tracking.repository;

import com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionChangeLogDao;
import org.springframework.data.mongodb.MongoExpression;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationUpdate;
import org.springframework.data.mongodb.core.aggregation.BucketOperationSupport;
import org.springframework.data.mongodb.core.query.*;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class CustomTrackingRepositoryImpl implements CustomTrackingRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public CustomTrackingRepositoryImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Mono<Void> recordAmmunitionChange(AmmunitionChangeLogDao change) {

        var lowerBound = OffsetDateTime.of(2023, 4, 1, 0, 0, 0, 0, ZoneOffset.UTC);
        var upperBound = OffsetDateTime.of(2023, 6, 30, 0, 0, 0, 0, ZoneOffset.UTC);
        var present = OffsetDateTime.now().plusMonths(2);
        CriteriaDefinition cd = Criteria.where("userId").is(change.getUserId())
                .and("startDate").lt(present)
                .and("endDate").gt(present);

        Query q = Query.query(cd);

        UpdateDefinition ud = new Update()
                .set("userId", change.getUserId())
                .set("startDate", lowerBound)
                .set("endDate", upperBound)
                .push("entries", change);

        return reactiveMongoTemplate.upsert(q, ud, AmmunitionChangeBucketEntry.class)
                .map(u -> u.wasAcknowledged())
                .log()
                .then();

    }
}

package com.github.szilex94.edu.round_tracker.repository.tracking.adapter;

import com.github.szilex94.edu.round_tracker.repository.tracking.TrackingDaoMapper;
import com.github.szilex94.edu.round_tracker.repository.tracking.repository.TrackingRepository;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChange;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChangeLog;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

@Component
public class TrackingRepositoryAdapterImpl implements TrackingRepositoryAdapter {

    private final TrackingRepository repository;

    private final TrackingDaoMapper mapper;

    public TrackingRepositoryAdapterImpl(TrackingRepository repository, TrackingDaoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<AmmunitionChangeLog> logAmmunitionChange(AmmunitionChange change) {
        //TODO look into using the DB to generate TIME STAMP
        var newLog = mapper.fromAmmunitionChange(change)
                .setRecordedAt(OffsetDateTime.now());

        return repository.save(newLog)
                .map(mapper::fromDao);
    }
}

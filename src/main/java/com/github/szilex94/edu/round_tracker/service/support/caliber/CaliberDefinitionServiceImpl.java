package com.github.szilex94.edu.round_tracker.service.support.caliber;

import com.github.szilex94.edu.round_tracker.repository.support.caliber.CaliberDefinitionRepositoryAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.google.common.base.Preconditions.checkArgument;

@Service
@Slf4j
public class CaliberDefinitionServiceImpl implements CaliberDefinitionService {

    private final CaliberDefinitionRepositoryAdapter repositoryAdapter;

    public CaliberDefinitionServiceImpl(CaliberDefinitionRepositoryAdapter repositoryAdapter) {
        this.repositoryAdapter = repositoryAdapter;
    }

    @Override
    public Mono<CaliberTypeDefinition> createNewCaliberDefinition(CaliberTypeDefinition caliberDef) {
        checkArgument(caliberDef != null, "Null input not allowed!");
        return repositoryAdapter.findByCode(caliberDef.getCode())
                .doOnNext(this::handleDuplicateCaliberType)
                .switchIfEmpty(repositoryAdapter.save(caliberDef));
    }

    private void handleDuplicateCaliberType(CaliberTypeDefinition def) {
        var code = def.getCode();
        log.warn("Attempted insertion of duplicate caliber code {}!", code);
        throw new DuplicateCaliberCodeException("Duplicate claiber code detected!", code);
    }
}

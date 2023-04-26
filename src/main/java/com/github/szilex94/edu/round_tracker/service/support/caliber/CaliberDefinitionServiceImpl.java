package com.github.szilex94.edu.round_tracker.service.support.caliber;

import com.github.szilex94.edu.round_tracker.repository.support.caliber.CaliberDefinitionRepositoryAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.UnaryOperator;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

@Service
@Slf4j
public class CaliberDefinitionServiceImpl implements CaliberDefinitionService {

    private final CaliberDefinitionRepositoryAdapter repositoryAdapter;

    public CaliberDefinitionServiceImpl(CaliberDefinitionRepositoryAdapter repositoryAdapter) {
        this.repositoryAdapter = repositoryAdapter;
    }

    @Override
    public Flux<CaliberTypeDefinition> retrieveCaliberDefinitions() {
        return repositoryAdapter.retrieveAll();
    }

    @Override
    public Mono<CaliberTypeDefinition> findByCode(String code) {
        checkArgument(!isNullOrEmpty(code), "Null or empty code not allowed!");
        return repositoryAdapter.findByCode(code);
    }

    @Override
    public Mono<CaliberTypeDefinition> createNewCaliberDefinition(CaliberTypeDefinition caliberDef) {
        checkArgument(caliberDef != null, "Null input not allowed!");
        return repositoryAdapter.findByCode(caliberDef.getCode())
                .doOnNext(this::handleDuplicateCaliberType)
                .switchIfEmpty(repositoryAdapter.save(caliberDef));
    }

    @Override
    public Mono<CaliberTypeDefinition> updateExisting(String code, UnaryOperator<CaliberTypeDefinition> withExisting) {
        checkArgument(!isNullOrEmpty(code), "Null or empty code not allowed!");
        checkArgument(withExisting != null, "Null updater function not allowed!");
        return repositoryAdapter.findByCode(code)
                .map(existing -> applyUpdate(existing, withExisting))
                .flatMap(repositoryAdapter::save);
    }

    private CaliberTypeDefinition applyUpdate(CaliberTypeDefinition input,
                                              UnaryOperator<CaliberTypeDefinition> withExisting) {
        var code = input.getCode();
        var result = withExisting.apply(input);
        if (!code.equals(result.getCode())) {
            log.error("An illegal 'code' update was attempted for code '{}'!", code);
            throw new IllegalStateException("Illegal definition code update for " + code);
        }

        return result;
    }

    private void handleDuplicateCaliberType(CaliberTypeDefinition def) {
        var code = def.getCode();
        log.warn("Attempted insertion of duplicate caliber code '{}'!", code);
        throw new DuplicateCaliberCodeException("Duplicate caliber code detected!", code);
    }
}

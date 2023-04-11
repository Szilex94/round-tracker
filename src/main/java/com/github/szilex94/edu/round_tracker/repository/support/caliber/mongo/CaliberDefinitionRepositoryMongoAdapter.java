package com.github.szilex94.edu.round_tracker.repository.support.caliber.mongo;

import com.github.szilex94.edu.round_tracker.repository.support.caliber.CaliberDefinitionRepositoryAdapter;
import com.github.szilex94.edu.round_tracker.service.support.caliber.CaliberTypeDefinition;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

@Component
public class CaliberDefinitionRepositoryMongoAdapter implements CaliberDefinitionRepositoryAdapter {

    private final CaliberDefinitionDaoMapper mapper;

    private final CaliberDefinitionRepository repository;

    public CaliberDefinitionRepositoryMongoAdapter(CaliberDefinitionDaoMapper mapper, CaliberDefinitionRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Mono<CaliberTypeDefinition> findByCode(String code) {
        checkArgument(!isNullOrEmpty(code), "Null or empty code not allowed!");
        return repository.findByCode(code)
                .map(mapper::fromDao);
    }

    @Override
    public Flux<CaliberTypeDefinition> retrieveAll() {
        return repository.findAll()
                .map(mapper::fromDao);
    }

    @Override
    public Mono<CaliberTypeDefinition> save(CaliberTypeDefinition input) {
        checkArgument(input != null, "Null input not allowed!");
        var dao = mapper.toDao(input);
        return repository.save(dao)
                .map(mapper::fromDao);
    }
}

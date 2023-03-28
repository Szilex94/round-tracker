package com.github.szilex94.edu.round_tracker.service.support.caliber;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CaliberDefinitionServiceImpl implements CaliberDefinitionService {
    @Override
    public Mono<CaliberTypeDefinition> createNewCaliberDefinition(CaliberTypeDefinition caliberDef) {
        throw new UnsupportedOperationException("TBD");
    }
}

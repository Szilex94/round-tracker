package com.github.szilex94.edu.round_tracker.rest.support.caliber;

import com.github.szilex94.edu.round_tracker.rest.jakarta.OnCreate;
import com.github.szilex94.edu.round_tracker.rest.jakarta.OnUpdate;
import com.github.szilex94.edu.round_tracker.service.support.caliber.CaliberDefinitionService;
import com.github.szilex94.edu.round_tracker.service.support.caliber.CaliberTypeDefinition;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_NDJSON_VALUE;

/**
 * Encompasses support operations like managing supported calibers or other configurations required by the application
 *
 * @author szilex94
 */
@RestController
@RequestMapping("round-tracker/v1/support/caliber")
public class CaliberController {

    private final CaliberTypeMapper mapper;

    private final CaliberDefinitionService caliberService;

    public CaliberController(CaliberTypeMapper mapper, CaliberDefinitionService caliberService) {
        this.mapper = mapper;
        this.caliberService = caliberService;
    }

    @GetMapping(produces = APPLICATION_NDJSON_VALUE)
    public Flux<CaliberTypeDefinitionDto> getSupportedCalibers() {
        return caliberService.retrieveCaliberDefinitions()
                .map(mapper::toDto);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CaliberTypeDefinitionDto> addCaliberDefinition(@Validated(OnCreate.class) @RequestBody CaliberTypeDefinitionDto dto) {
        var def = mapper.fromDto(dto);
        return caliberService.createNewCaliberDefinition(def)
                .map(mapper::toDto);
    }

    @PatchMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Mono<CaliberTypeDefinitionDto> patchCaliberDefinition(@Validated(OnUpdate.class) @RequestBody CaliberTypeDefinitionDto dto) {
        return this.caliberService.updateExisting(dto.code(), existing -> applyPatch(existing, dto))
                .switchIfEmpty(Mono.error(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(this.mapper::toDto);
    }

    private static CaliberTypeDefinition applyPatch(CaliberTypeDefinition existing, CaliberTypeDefinitionDto desired) {
        var result = existing.toBuilder();

        var displayName = desired.displayName();
        if (displayName != null) {
            result.setDisplayName(displayName);
        }

        var description = desired.description();
        if (description != null) {
            result.setDescription(description);
        }

        return result.build();
    }

}

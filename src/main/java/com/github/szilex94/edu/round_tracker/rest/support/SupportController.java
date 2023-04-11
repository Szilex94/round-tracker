package com.github.szilex94.edu.round_tracker.rest.support;

import com.github.szilex94.edu.round_tracker.rest.jakarta.OnCreate;
import com.github.szilex94.edu.round_tracker.rest.jakarta.OnUpdate;
import com.github.szilex94.edu.round_tracker.service.support.caliber.CaliberDefinitionService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping("round-tracker/v1/support")
public class SupportController {

    private final CaliberTypeMapper mapper;

    private final CaliberDefinitionService caliberService;

    public SupportController(CaliberTypeMapper mapper, CaliberDefinitionService caliberService) {
        this.mapper = mapper;
        this.caliberService = caliberService;
    }

    @GetMapping(path = "/caliberDefinition", produces = APPLICATION_NDJSON_VALUE)
    public Flux<CaliberTypeDefinitionDto> getSupportedCalibers() {
        return caliberService.retrieveCaliberDefinitions()
                .map(mapper::toDto);
    }

    @PostMapping(path = "/caliberDefinition", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CaliberTypeDefinitionDto> addCaliberDefinition(@Validated(OnCreate.class) @RequestBody CaliberTypeDefinitionDto dto) {
        var def = mapper.fromDto(dto);
        return caliberService.createNewCaliberDefinition(def)
                .map(mapper::toDto);
    }

    @PatchMapping(path = "/caliberDefinition", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Mono<CaliberTypeDefinitionDto> patchCaliberDefinition(@Validated(OnUpdate.class) @RequestBody CaliberTypeDefinitionDto dto) {
        throw new UnsupportedOperationException("TBD");
    }

}

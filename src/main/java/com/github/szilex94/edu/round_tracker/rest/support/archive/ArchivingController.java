package com.github.szilex94.edu.round_tracker.rest.support.archive;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("round-tracker/v1/support/archive")
public class ArchivingController {

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Mono<ArchiveStatusDTO> getLastArchiveRunStatus() {
        throw new UnsupportedOperationException("TBD");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> triggerArchivingProcess() {
        throw new UnsupportedOperationException("TBD");
    }

}

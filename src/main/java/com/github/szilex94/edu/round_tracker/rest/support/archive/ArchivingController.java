package com.github.szilex94.edu.round_tracker.rest.support.archive;

import com.github.szilex94.edu.round_tracker.service.datamanagement.archiving.ArchivingService;
import com.github.szilex94.edu.round_tracker.service.datamanagement.archiving.ArchivingSubmissionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("round-tracker/v1/support/archive")
public class ArchivingController {

    private final ArchivingService archivingService;

    public ArchivingController(ArchivingService archivingService) {
        this.archivingService = archivingService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Mono<ArchiveStatusDTO> getLastArchiveRunStatus() {
        throw new UnsupportedOperationException("TBD");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<ArchiveStatusDTO> triggerArchivingProcess() {
        return archivingService.triggerArchivingProcess()
                .map(this::convertToResponse);
    }

    private ArchiveStatusDTO convertToResponse(ArchivingSubmissionRequest in) {
        //TODO proper message conversion
        return new ArchiveStatusDTO();
    }

}

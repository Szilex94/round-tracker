package com.github.szilex94.edu.round_tracker.rest.support.archive;

import com.github.szilex94.edu.round_tracker.service.datamanagement.archiving.ArchivingService;
import com.github.szilex94.edu.round_tracker.service.datamanagement.archiving.ArchivingSubmissionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public Mono<ResponseEntity<ArchiveStatusDTO>> triggerArchivingProcess() {
        return archivingService.triggerArchivingProcess()
                .map(this::convertToResponse);
    }

    private ResponseEntity<ArchiveStatusDTO> convertToResponse(ArchivingSubmissionRequest submissionResult) {
        boolean wasAccepted = submissionResult.accepted();
        return (wasAccepted ? ResponseEntity.accepted() : ResponseEntity.ok())
                .body(new ArchiveStatusDTO(wasAccepted, submissionResult.detail().orElse("N/A")));
    }

}

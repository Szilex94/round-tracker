package com.github.szilex94.edu.round_tracker.rest.tracking.summary;

import com.github.szilex94.edu.round_tracker.service.tracking.summary.SummaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("round-tracker/v1/tracking/user/{userId}/summary")
public class SummaryController {

    private final TrackingSummaryDtoMapper mapper;

    private final SummaryService summaryService;

    public SummaryController(TrackingSummaryDtoMapper mapper, SummaryService summaryService) {
        this.mapper = mapper;
        this.summaryService = summaryService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Mono<TrackingSummaryDto> getSummary(@PathVariable String userId) {
        return summaryService.summaryForUser(userId)
                .map(mapper::toDto);
    }

}

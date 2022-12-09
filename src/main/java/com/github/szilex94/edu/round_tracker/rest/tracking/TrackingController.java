package com.github.szilex94.edu.round_tracker.rest.tracking;

import com.github.szilex94.edu.round_tracker.rest.tracking.mapper.TrackingMapper;
import com.github.szilex94.edu.round_tracker.rest.tracking.model.AmmunitionChangeDto;
import com.github.szilex94.edu.round_tracker.rest.tracking.model.UserAmmunitionSummaryDto;
import com.github.szilex94.edu.round_tracker.service.tracking.AmmunitionExpenseTrackingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("round-tracker/v1/tracking/user/{userId}")
public class TrackingController {


    private final TrackingMapper mapper;

    private final AmmunitionExpenseTrackingService trackingService;

    public TrackingController(TrackingMapper mapper,
                              AmmunitionExpenseTrackingService trackingService) {
        this.mapper = mapper;
        this.trackingService = trackingService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public Mono<UserAmmunitionSummaryDto> trackChange(@PathVariable String userId,
                                                      @Valid @RequestBody AmmunitionChangeDto expenseEntryDto) {
        //TODO add check for changeType to prevent unknown
        var change = mapper.fromDto(userId, expenseEntryDto);

        return trackingService.recordAmmunitionChange(change)
                .map(mapper::toDto);

    }
}

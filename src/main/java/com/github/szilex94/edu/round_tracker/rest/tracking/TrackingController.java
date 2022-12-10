package com.github.szilex94.edu.round_tracker.rest.tracking;

import com.github.szilex94.edu.round_tracker.rest.tracking.mapper.TrackingMapper;
import com.github.szilex94.edu.round_tracker.rest.tracking.model.AmmunitionChangeDto;
import com.github.szilex94.edu.round_tracker.rest.tracking.model.AmmunitionTypeDto;
import com.github.szilex94.edu.round_tracker.rest.tracking.model.UserAmmunitionSummaryDto;
import com.github.szilex94.edu.round_tracker.service.tracking.AmmunitionExpenseTrackingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("round-tracker/v1/user/{userId}/tracking")
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

        if (expenseEntryDto.ammunitionType() == AmmunitionTypeDto.UNKNOWN) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        
        var change = mapper.fromDto(userId, expenseEntryDto);

        return trackingService.recordAmmunitionChange(change)
                .map(mapper::toDto);

    }
}

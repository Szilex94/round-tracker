package com.github.szilex94.edu.round_tracker.rest.tracking.log;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("round-tracker/v1/tracking/log")
public class TrackingLogController {

    @PostMapping
    public void logNew(){

    }
}

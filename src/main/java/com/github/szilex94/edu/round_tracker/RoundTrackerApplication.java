package com.github.szilex94.edu.round_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


@SpringBootApplication
@ConfigurationPropertiesScan("com.github.szilex94.edu.round_tracker.configuration.app")
public class RoundTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoundTrackerApplication.class, args);
    }

}

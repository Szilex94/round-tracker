package com.github.szilex94.edu.round_tracker.configuration.app;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@Validated
@ConfigurationProperties("app.archiving")
public record ArchivingConfiguration(
        @Min(value = 100, message = "Minimum bucket size is restricted to 10!")
        @Max(value = 1_000, message = "Max bucket size is restricted to 1000!")
        int bucketSize
) {

}

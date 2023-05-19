package com.github.szilex94.edu.round_tracker.service.datamanagement.archiving;

import com.github.szilex94.edu.round_tracker.service.datamanagement.archiving.time.ArchivingTimeSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArchivingServiceConfiguration {

    @Bean
    public ArchivingTimeSupplier timeSupplier() {
        //TODO Introduce config in prop file and adequate implementations
        return ArchivingTimeSupplier.withPresentDateAsCutoff();
    }
}

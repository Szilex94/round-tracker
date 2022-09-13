package com.github.szilex94.edu.round_tracker.config;

import com.github.szilex94.edu.round_tracker.service.user.profile.ProfileIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public ProfileIdGenerator profileIdGenerator() {
        return ProfileIdGenerator.defaultImpl();
    }
}

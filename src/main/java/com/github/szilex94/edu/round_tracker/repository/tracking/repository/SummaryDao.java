package com.github.szilex94.edu.round_tracker.repository.tracking.repository;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user.tracking.summary")
public record SummaryDao(
        @Indexed
        String userId,

        int nineMillimeter) {
}

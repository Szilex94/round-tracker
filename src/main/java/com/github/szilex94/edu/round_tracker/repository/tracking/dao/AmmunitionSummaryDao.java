package com.github.szilex94.edu.round_tracker.repository.tracking.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document("user.tracking.summary")
public record AmmunitionSummaryDao(
        @Id
        String id,

        @Indexed
        String userId,

        Map<String, Long> codeToTotal) {

    /**
     * FIELD_USER_ID - denotes the user id field in the document, which can be used when programmatically building queries
     */
    public static final String FIELD_USER_ID = "userId";

    /**
     * FIELD_USER_ID - denotes the ammunitionCode to grand total in the document, which can be used when programmatically building queries
     */
    public static final String FIELD_CODE_TO_TOTAL = "codeToTotal";
}

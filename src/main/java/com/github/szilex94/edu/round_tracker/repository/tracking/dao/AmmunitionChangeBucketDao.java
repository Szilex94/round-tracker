package com.github.szilex94.edu.round_tracker.repository.tracking.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.List;

@Document(collection = "user.tracking.archive")
public record AmmunitionChangeBucketDao(
        @Id
        String id,
        @Indexed
        String userId,
        String ammunitionCode,
        OffsetDateTime oldestEntryRecordedAt,
        OffsetDateTime latestEntryRecordedAt,
        List<ArchivedAmmunitionChangeLog> entries,
        long totalAmount,
        int entryCount
) {

    public static final String FIELD_USER_ID = "userId";

    public static final String FIELD_AMMUNITION_CODE = "ammunitionCode";

    public static final String FIELD_OLDEST_ENTRY_TIME_STAMP = "oldestEntryRecordedAt";

    public static final String FIELD_LATEST_ENTRY_TIME_STAMP = "latestEntryRecordedAt";

    public static final String FIELD_ENTRIES = "entries";

    public static final String FIELD_TOTAL_AMOUNT = "totalAmount";

    public static final String FIELD_ENTRY_COUNT = "entryCount";


    public record ArchivedAmmunitionChangeLog(
            String id,
            OffsetDateTime recordedAt,
            ChangeTypeDao changeType,
            int amount
    ) {

    }
}

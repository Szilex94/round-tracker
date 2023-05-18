package com.github.szilex94.edu.round_tracker.repository.tracking.dao;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.TimeSeries;
import org.springframework.data.mongodb.core.timeseries.Granularity;

import java.time.OffsetDateTime;


@Data
@Accessors(chain = true)
@TimeSeries(collection = "user.tracking.log",
        timeField = "recordedAt",
        metaField = "userId",
        granularity = Granularity.HOURS)
public class AmmunitionChangeLogDao {

    public static final String FIELD_RECORDED_AT = "recordedAt";

    public static final String FIELD_MARKED_FOR_ARCHIVING = "markedForArchiving";

    @Id
    private String id;

    @Indexed
    private String userId;

    private OffsetDateTime recordedAt;

    private String ammunitionCode;

    private ChangeTypeDao changeType;

    private int amount;

    private boolean markedForArchiving;

}

package com.github.szilex94.edu.round_tracker.repository.tracking.repository;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@Document("user.tracking.log")
@Data
@Accessors(chain = true)
public class AmmunitionChangeBucketEntry {

    @Id
    private String id;

    @Indexed
    private String userId;

    private OffsetDateTime timeRecorded;

    private int ammunitionChange;


}

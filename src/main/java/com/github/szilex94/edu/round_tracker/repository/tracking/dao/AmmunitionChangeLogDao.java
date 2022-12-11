package com.github.szilex94.edu.round_tracker.repository.tracking.dao;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@Data
@Accessors(chain = true)
@Document("user.tracking.log")
public class AmmunitionChangeLogDao {

    @Id
    private String logId;

    private String userId;

    private int amount;

    private ChangeTypeDao changeType;

    private AmmunitionTypeDao ammunitionType;

    @CreatedDate
    private OffsetDateTime recordedAt;
}

package com.github.szilex94.edu.round_tracker.service.tracking.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

@Getter
@EqualsAndHashCode
@ToString
public final class AmmunitionChangeLog {

    private final String logId;

    private final String userId;

    private final int amount;

    private final ChangeType changeType;

    private final AmmunitionType ammunitionType;

    private final OffsetDateTime timeRecorded;


    AmmunitionChangeLog(Builder builder) {
        this.logId = builder.logId;
        this.userId = builder.userId;
        this.amount = builder.amount;
        this.changeType = builder.changeType;
        this.ammunitionType = builder.ammunitionType;
        this.timeRecorded = builder.recordedTime;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        String logId;

        String userId;

        int amount;

        ChangeType changeType;

        AmmunitionType ammunitionType;

        OffsetDateTime recordedTime;

        public Builder setLogId(String logId) {
            this.logId = logId;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder setChangeType(ChangeType changeType) {
            this.changeType = changeType;
            return this;
        }

        public Builder setAmmunitionType(AmmunitionType ammunitionType) {
            this.ammunitionType = ammunitionType;
            return this;
        }

        public Builder setRecordedTime(OffsetDateTime recordedTime) {
            this.recordedTime = recordedTime;
            return this;
        }

        public AmmunitionChangeLog build() {
            checkArgument(!isNullOrEmpty(logId), "LogId not set!");
            checkArgument(!isNullOrEmpty(userId), "UserId not set!");
            checkArgument(changeType != null, "ChangeType not set!");
            checkArgument(ammunitionType != null, "AmmunitionType not set!");
            checkArgument(recordedTime != null, "RecordedTime not set!");
            return new AmmunitionChangeLog(this);
        }
    }


}

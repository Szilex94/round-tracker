package com.github.szilex94.edu.round_tracker.service.tracking.model;

import com.google.common.base.MoreObjects;

import java.time.OffsetDateTime;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Represents the change to a users available ammunition
 *
 * @author szilex94
 */
public final class AmmunitionChange {

    private final String id;

    private final String userId;

    private final OffsetDateTime recordedAt;

    private final String ammunitionCode;

    private final int amount;

    private final ChangeType changeType;

    private final boolean archived;

    AmmunitionChange(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.recordedAt = builder.recordedAt;
        this.ammunitionCode = builder.ammunitionCode;
        this.amount = builder.amount;
        this.changeType = builder.changeType;
        this.archived = builder.archived;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public OffsetDateTime getRecordedAt() {
        return recordedAt;
    }

    public int getAmount() {
        return amount;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public String getAmmunitionCode() {
        return ammunitionCode;
    }

    public boolean isArchived() {
        return archived;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("ammunitionCode", ammunitionCode)
                .add("amount", amount)
                .add("changeType", changeType)
                .add("archived", archived)
                .toString();
    }

    public static final class Builder {

        String id;

        String userId;

        OffsetDateTime recordedAt;

        String ammunitionCode;

        int amount;

        ChangeType changeType;

        boolean archived;

        private Builder() {
            //Restrict visibility
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setRecordedAt(OffsetDateTime recordedAt) {
            this.recordedAt = recordedAt;
            return this;
        }

        public Builder withPresentAsRecordedAt() {
            return this.setRecordedAt(OffsetDateTime.now());
        }

        public Builder setAmmunitionCode(String ammunitionCode) {
            this.ammunitionCode = ammunitionCode;
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

        public Builder setArchived(boolean archived) {
            this.archived = archived;
            return this;
        }

        public AmmunitionChange build() {
            checkArgument(!isNullOrEmpty(userId), "User Id not set!");
            checkArgument(recordedAt != null, "Time of recording must not be null!");
            checkArgument(!isNullOrEmpty(ammunitionCode), "Ammunition code not set!");
            checkArgument(amount != 0, "Amount must be equal to zero!");
            checkArgument(changeType != null, "Change Type not set!");
            return new AmmunitionChange(this);
        }
    }


}

package com.github.szilex94.edu.round_tracker.service.tracking.model;

import com.google.common.base.MoreObjects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Represents the change to a users available ammunition
 *
 * @author szilex94
 */
public final class AmmunitionChange {

    private final String userId;

    private final int amount;

    private final ChangeType changeType;

    private final AmmunitionType ammunitionType;

    AmmunitionChange(Builder builder) {
        this.userId = builder.userId;
        this.amount = builder.amount;
        this.changeType = builder.changeType;
        this.ammunitionType = builder.ammunitionType;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getUserId() {
        return userId;
    }

    public int getAmount() {
        return amount;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public AmmunitionType getAmmunitionType() {
        return ammunitionType;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("amount", amount)
                .add("changeType", changeType)
                .add("ammunitionType", ammunitionType)
                .toString();
    }

    public static final class Builder {
        String userId;

        int amount;

        ChangeType changeType;

        AmmunitionType ammunitionType;

        private Builder() {
            //Restrict visibility
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

        public AmmunitionChange build() {
            checkArgument(!isNullOrEmpty(userId), "User Id not set!");
            checkArgument(amount > 0, "Amount must be greater than 0!");
            checkArgument(changeType != null, "Change Type not set!");
            checkArgument(ammunitionType != null, "Ammunition Type not set!");
            return new AmmunitionChange(this);
        }
    }


}

package com.github.szilex94.edu.round_tracker.service.tracking.model;

import com.google.common.base.MoreObjects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

public final class UserAmmunitionSummary {

    private final String userId;

    //TODO lastModified?

    private final int grandTotal;

    public String getUserId() {
        return userId;
    }

    public int getGrandTotal() {
        return grandTotal;
    }

    UserAmmunitionSummary(Builder builder) {
        this.userId = builder.userId;
        this.grandTotal = builder.grandTotal;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("totalCount", grandTotal)
                .toString();
    }

    public static final class Builder {
        String userId;

        int grandTotal;

        private Builder() {

        }

        public Builder forUser(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setGrandTotal(int grandTotal) {
            this.grandTotal = grandTotal;
            return this;
        }

        public UserAmmunitionSummary build() {
            checkArgument(!isNullOrEmpty(userId), "UserId not set!");
            return new UserAmmunitionSummary(this);
        }
    }
}

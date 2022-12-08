package com.github.szilex94.edu.round_tracker.service.tracking.model;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

public final class UserAmmunitionSummary {

    private final String userId;

    private final Map<AmmunitionType, Integer> typeToCount;

    public String getUserId() {
        return userId;
    }

    public Map<AmmunitionType, Integer> getTypeToCount() {
        return typeToCount;
    }

    UserAmmunitionSummary(Builder builder) {
        this.userId = builder.userId;
        this.typeToCount = builder.typeToCount.build();
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "UserAmmunitionSummary{" +
                "userId='" + userId + '\'' +
                ", typeToCount=" + typeToCount +
                '}';
    }

    public static final class Builder {
        String userId;

        ImmutableMap.Builder<AmmunitionType, Integer> typeToCount;

        private Builder() {
            this.typeToCount = ImmutableMap.builder();
        }

        public Builder forUser(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder addSummary(AmmunitionType type, int amount) {
            checkArgument(type != null, "Null type not allowed!");
            this.typeToCount.put(type, amount);
            return this;
        }

        public UserAmmunitionSummary build() {
            checkArgument(!isNullOrEmpty(userId), "UserId not set!");
            return new UserAmmunitionSummary(this);
        }
    }
}

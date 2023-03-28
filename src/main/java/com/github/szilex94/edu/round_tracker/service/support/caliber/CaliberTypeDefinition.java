package com.github.szilex94.edu.round_tracker.service.support.caliber;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

public final class CaliberTypeDefinition {

    private final String code;

    private final String displayName;

    private final String description;

    CaliberTypeDefinition(Builder builder) {
        this.code = builder.code;
        this.displayName = builder.displayName;
        this.description = builder.description;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public static class Builder {
        String code;

        String displayName;

        String description;

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public CaliberTypeDefinition build() {
            checkArgument(!isNullOrEmpty(this.code), "Null or empty code not allowed!");
            if (displayName == null) {
                this.displayName = this.code;
            }
            return new CaliberTypeDefinition(this);
        }
    }
}

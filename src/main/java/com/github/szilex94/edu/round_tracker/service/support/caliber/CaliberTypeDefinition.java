package com.github.szilex94.edu.round_tracker.service.support.caliber;

import com.google.common.base.MoreObjects;

import java.util.Objects;

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

    public Builder toBuilder() {
        return builder()
                .setCode(this.code)
                .setDisplayName(this.displayName)
                .setDescription(this.description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CaliberTypeDefinition that = (CaliberTypeDefinition) o;

        if (!code.equals(that.code)) return false;
        if (!Objects.equals(displayName, that.displayName)) return false;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", code)
                .add("displayName", displayName)
                .add("description", description)
                .toString();
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

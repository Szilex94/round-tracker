package com.github.szilex94.edu.round_tracker.service.tracking.model;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

@Getter
@ToString
public final class AmmunitionChangeSummary {

    private final String id;

    private final String userId;

    private final Map<String, SummaryEntry> codeToSummary;


    AmmunitionChangeSummary(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.codeToSummary = builder.summaryEntries.build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public record SummaryEntry(
            String ammunitionCode,
            long grandTotal,
            OffsetDateTime lastChangeRecordedAt
    ) {
    }


    public static final class Builder {
        String id;

        String userId;

        ImmutableMap.Builder<String, SummaryEntry> summaryEntries = ImmutableMap.builder();

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public SummaryBuilder newSummaryEntryForCode(String ammunitionCode) {
            return new SummaryBuilder(this, ammunitionCode);
        }

        public AmmunitionChangeSummary build() {
            checkArgument(!isNullOrEmpty(id), "LogId not set!");
            checkArgument(!isNullOrEmpty(userId), "UserId not set!");
            return new AmmunitionChangeSummary(this);
        }

        public static class SummaryBuilder {

            private final Builder parentBuilder;

            String ammunitionCode;
            long grandTotal;
            OffsetDateTime lastChangeRecordedAt;

            public SummaryBuilder(Builder parentBuilder, String ammunitionCode) {
                this.parentBuilder = parentBuilder;
                this.ammunitionCode = ammunitionCode;
            }

            public SummaryBuilder setGrandTotal(long grandTotal) {
                this.grandTotal = grandTotal;
                return this;
            }

            public SummaryBuilder setLastChangeRecordedAt(OffsetDateTime lastChangeRecordedAt) {
                this.lastChangeRecordedAt = lastChangeRecordedAt;
                return this;
            }

            public Builder buildAndReturnToParent() {
                checkArgument(ammunitionCode != null, "AmmunitionCode not set!");
                checkArgument(lastChangeRecordedAt != null, "Last known change record time not set!");
                this.parentBuilder.summaryEntries.put(this.ammunitionCode,
                        new SummaryEntry(this.ammunitionCode, grandTotal, lastChangeRecordedAt));
                return parentBuilder;
            }
        }
    }


}

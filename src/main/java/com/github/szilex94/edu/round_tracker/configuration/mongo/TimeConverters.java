package com.github.szilex94.edu.round_tracker.configuration.mongo;

import org.springframework.core.convert.converter.Converter;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static com.google.common.base.Preconditions.checkArgument;

final class TimeConverters {

    private TimeConverters() {
        //Suppress default constructor
    }

    static final class MongoOffsetDateTimeWriter implements Converter<OffsetDateTime, Date> {

        @Override
        public Date convert(OffsetDateTime source) {
            return Date.from(source.toInstant());
        }
    }

    static final class MongoOffsetDateTimeReader implements Converter<Date, OffsetDateTime> {

        private final ZoneOffset atOffset;

        public MongoOffsetDateTimeReader() {
            this(ZoneOffset.UTC);
        }

        public MongoOffsetDateTimeReader(ZoneOffset atOffset) {
            checkArgument(atOffset != null, "Null offset not allowed!");
            this.atOffset = atOffset;
        }

        @Override
        public OffsetDateTime convert(Date date) {
            return OffsetDateTime.ofInstant(date.toInstant(), atOffset);
        }

    }
}

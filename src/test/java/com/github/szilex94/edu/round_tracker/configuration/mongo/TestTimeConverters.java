package com.github.szilex94.edu.round_tracker.configuration.mongo;

import com.github.szilex94.edu.round_tracker.configuration.mongo.TimeConverters.MongoOffsetDateTimeReader;
import com.github.szilex94.edu.round_tracker.configuration.mongo.TimeConverters.MongoOffsetDateTimeWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for {@link TimeConverters}
 *
 * @author szilex94
 */
public class TestTimeConverters {

    private MongoOffsetDateTimeWriter timeWriterSubject;

    private MongoOffsetDateTimeReader timeReaderSubject;

    @BeforeEach
    public void beforeEach() {
        timeWriterSubject = new MongoOffsetDateTimeWriter();
        timeReaderSubject = new MongoOffsetDateTimeReader();
    }

    @Test
    public void test_MongoOffsetDateTimeWriter_atZero() {
        var instantAtZero = Instant.ofEpochMilli(0);
        var offsetDateTimeAtZero = OffsetDateTime.ofInstant(instantAtZero, ZoneOffset.UTC);

        Date result = timeWriterSubject.convert(offsetDateTimeAtZero);

        assertEquals(Date.from(instantAtZero), result);
    }

    @Test
    public void test_MongoOffsetDateTimeReader_atZero() {
        var instantAtZero = Instant.ofEpochMilli(0);
        var dateTimeAtZero = Date.from(instantAtZero);

        OffsetDateTime result = timeReaderSubject.convert(dateTimeAtZero);

        assertEquals(OffsetDateTime.ofInstant(instantAtZero, ZoneOffset.UTC), result);
    }

    @Test
    public void test_chainedConversions() {
        var timeNow = OffsetDateTime.now(ZoneOffset.UTC);

        var date = timeWriterSubject.convert(timeNow);
        var offsetDateTime = timeReaderSubject.convert(date);

        assertEquals(timeNow.getYear(), offsetDateTime.getYear());
        assertEquals(timeNow.getMonth(), offsetDateTime.getMonth());
        assertEquals(timeNow.getDayOfWeek(), offsetDateTime.getDayOfWeek());
        assertEquals(timeNow.getHour(), offsetDateTime.getHour());
        assertEquals(timeNow.getMinute(), offsetDateTime.getMinute());
        assertEquals(timeNow.getSecond(), offsetDateTime.getSecond());

    }
}

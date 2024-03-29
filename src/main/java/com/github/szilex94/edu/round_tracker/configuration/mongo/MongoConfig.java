package com.github.szilex94.edu.round_tracker.configuration.mongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.transaction.ReactiveTransactionManager;

import java.util.Arrays;

/**
 * Contains configuration required for the MongoDB (such as serialization/deserialization of time stamps etc.)
 *
 * @author szilex94
 */
@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(
                Arrays.asList(
                        new TimeConverters.MongoOffsetDateTimeWriter(),
                        new TimeConverters.MongoOffsetDateTimeReader()
                ));
    }


    @Bean
    public ReactiveTransactionManager reactiveTransactionManager(ReactiveMongoDatabaseFactory dbFactory) {
        return new ReactiveMongoTransactionManager(dbFactory);
    }


}

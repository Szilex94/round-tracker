package com.github.szilex94.edu.round_tracker.integration;

import com.github.szilex94.edu.round_tracker.rest.user.profile.UserProfileDto;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Contains support methods which are meant to be used in tests to cut down on duplication
 *
 * @author szilex94
 */
public class TestRestUtilities {

    private final int port;

    private final TestRestTemplate testRestTemplate;

    private static final AtomicInteger COUNT = new AtomicInteger();

    public TestRestUtilities(int port, TestRestTemplate testRestTemplate) {
        this.port = port;
        this.testRestTemplate = testRestTemplate;
    }

    public UserProfileDto createNewUser() {
        int current = COUNT.incrementAndGet();
        var desired = new UserProfileDto()
                .setFirstName("firstName" + "#" + current)
                .setLastName("lastName" + "#" + current)
                .setAlias("alias" + "#" + current);

        var result = this.testRestTemplate.postForEntity(getUserProfilePath().toUriString(), desired, UserProfileDto.class);

        assertSame(HttpStatus.CREATED, result.getStatusCode());
        return result.getBody();
    }

    private UriComponentsBuilder getUserProfilePath() {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(this.port)
                .path(Endpoints.USER_PROFILE);
    }
}

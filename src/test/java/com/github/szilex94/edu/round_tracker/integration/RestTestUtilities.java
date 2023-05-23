package com.github.szilex94.edu.round_tracker.integration;

import com.github.szilex94.edu.round_tracker.rest.support.caliber.CaliberTypeDefinitionDto;
import com.github.szilex94.edu.round_tracker.rest.user.profile.UserProfileDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Contains support methods which are meant to be used in tests to cut down on duplication
 *
 * @author szilex94
 */
public class RestTestUtilities {

    private final WebTestClient webTestClient;

    private static final AtomicInteger USER_COUNT = new AtomicInteger();

    private static final AtomicInteger CALIBER_DEFINITION_COUNT = new AtomicInteger();


    public RestTestUtilities(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    public UserProfileDto createNewUser() {
        int current = USER_COUNT.incrementAndGet();
        var desired = new UserProfileDto()
                .setFirstName("firstName#" + current)
                .setLastName("lastName#" + current)
                .setAlias("alias#" + current);

        return webTestClient.post().uri(Endpoints.USER_PROFILE)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(desired)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody(UserProfileDto.class)
                .returnResult()
                .getResponseBody();
    }

    /**
     * Creates a new and unique caliber type definition
     *
     * @return the created definition
     */
    public CaliberTypeDefinitionDto createNewCaliberTypeDefinition() {
        int current = CALIBER_DEFINITION_COUNT.incrementAndGet();

        //Note '#' is not an allowed character in code
        var desired = new CaliberTypeDefinitionDto(
                "code_" + current,
                "displayName#" + current,
                "description#" + current);

        return createNewCaliberTypeDefinition(desired);
    }

    /**
     * Creates a new definition using the supplied input
     *
     * @param desired the entity which will be submitted as the request body
     * @return the result of the request
     */
    public CaliberTypeDefinitionDto createNewCaliberTypeDefinition(CaliberTypeDefinitionDto desired) {

        return webTestClient.post()
                .uri(Endpoints.SUPPORT_CALIBER_DEF)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(desired)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody(CaliberTypeDefinitionDto.class)
                .returnResult()
                .getResponseBody();
    }

}

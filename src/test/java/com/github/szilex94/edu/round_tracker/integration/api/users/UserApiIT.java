package com.github.szilex94.edu.round_tracker.integration.api.users;

import com.github.szilex94.edu.round_tracker.integration.BaseTestContainerIT;
import com.github.szilex94.edu.round_tracker.integration.Endpoints;
import com.github.szilex94.edu.round_tracker.rest.error.ProblemDetailFactory;
import com.github.szilex94.edu.round_tracker.rest.error.codes.UserAPIError;
import com.github.szilex94.edu.round_tracker.rest.user.profile.UserProfileDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class UserApiIT extends BaseTestContainerIT {


    private static final String TEST_USER_FIRST_NAME = "testFirstName";

    private static final String TEST_USER_LAST_NAME = "testLastName";

    private static final String TEST_USER_ALIAS = "testAlias";

    private static final AtomicInteger COUNT = new AtomicInteger(0);

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void test_createUser() {
        UserProfileDto userDto = createRequestObject();

        var body = this.webTestClient.post().uri(Endpoints.USER_PROFILE).bodyValue(userDto)
                .exchange().expectStatus().isCreated()
                .expectBody(UserProfileDto.class)
                .returnResult().getResponseBody();

        assertNotNull(body);
        assertNotNull(body.getId());
        assertEquals(userDto.getFirstName(), body.getFirstName());
        assertEquals(userDto.getLastName(), body.getLastName());
        assertEquals(userDto.getAlias(), body.getAlias());
    }

    @Test
    public void test_createUser_duplicate() {
        UserProfileDto userDto = createRequestObject();

        webTestClient.post().uri(Endpoints.USER_PROFILE).bodyValue(userDto)
                .exchange()
                .expectStatus().isCreated();

        var expectedProblemDetail = webTestClient.post().uri(Endpoints.USER_PROFILE).bodyValue(userDto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();

        var additionalProperties = expectedProblemDetail.getProperties();

        assertEquals(UserAPIError.USER_PROFILE_UNIQUE_IDENTIFIER_CONFLICT.getApiErrorCode(), additionalProperties.get(ProblemDetailFactory.API_ERROR_CODE));
    }

    @ParameterizedTest
    @MethodSource("createUserExceptionalCasesDataSource")
    public void test_createUser_exceptionalCases(UserProfileDto requestBody) {
        this.webTestClient.post().uri(Endpoints.USER_PROFILE).bodyValue(requestBody)
                .exchange()
                .expectStatus().isBadRequest();
    }

    private static Stream<UserProfileDto> createUserExceptionalCasesDataSource() {
        return Stream.<UserProfileDto>builder()
                .add(createRequestObject().setFirstName(null))
                .add(createRequestObject().setFirstName(""))
                .add(createRequestObject().setLastName(null))
                .add(createRequestObject().setLastName(""))
                .build();
    }

    @Test
    public void test_retrieveUser() {
        UserProfileDto userDto = createRequestObject();

        var postUser = this.webTestClient.post().uri(Endpoints.USER_PROFILE).bodyValue(userDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserProfileDto.class).returnResult().getResponseBody();

        var userId = postUser.getId();

        var responseBody = webTestClient.get().uri(Endpoints.USER_PROFILE + "/" + userId).exchange()
                .expectStatus().isOk()
                .expectBody(UserProfileDto.class)
                .returnResult().getResponseBody();


        assertNotNull(responseBody);

        assertEquals(userDto.getFirstName(), responseBody.getFirstName());
        assertEquals(userDto.getLastName(), responseBody.getLastName());
        assertEquals(userDto.getAlias(), responseBody.getAlias());
    }

    @Test
    public void test_retrieveUser_userNotFound() {
        webTestClient.get().uri(Endpoints.USER_PROFILE + "/randomTextInsteadOfUserId").exchange()
                .expectStatus().isNotFound();
    }

    private static UserProfileDto createRequestObject() {
        int current = COUNT.incrementAndGet();
        return new UserProfileDto()
                .setFirstName(TEST_USER_FIRST_NAME + "#" + current)
                .setLastName(TEST_USER_LAST_NAME + "#" + current)
                .setAlias(TEST_USER_ALIAS + "#" + current);
    }

}

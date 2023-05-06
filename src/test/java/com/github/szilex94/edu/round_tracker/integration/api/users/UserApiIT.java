package com.github.szilex94.edu.round_tracker.integration.api.users;

import com.github.szilex94.edu.round_tracker.integration.BaseTestContainerIT;
import com.github.szilex94.edu.round_tracker.integration.Endpoints;
import com.github.szilex94.edu.round_tracker.rest.error.ApiErrorCodeEnum;
import com.github.szilex94.edu.round_tracker.rest.error.GenericErrorResponse;
import com.github.szilex94.edu.round_tracker.rest.user.profile.UserProfileDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


public class UserApiIT extends BaseTestContainerIT {


    private static final String TEST_USER_FIRST_NAME = "testFirstName";

    private static final String TEST_USER_LAST_NAME = "testLastName";

    private static final String TEST_USER_ALIAS = "testAlias";

    private static final AtomicInteger COUNT = new AtomicInteger(0);

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    public UriComponentsBuilder getBasePath() {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(this.port)
                .path(Endpoints.USER_PROFILE);
    }

    @Test
    public void tes_createUser() {
        UserProfileDto userDto = createRequestObject();

        var result = this.testRestTemplate.postForEntity(getBasePath().toUriString(), userDto, UserProfileDto.class);

        assertSame(HttpStatus.CREATED, result.getStatusCode());
        var body = result.getBody();
        assertNotNull(body);
        assertNotNull(body.getId());
        assertEquals(userDto.getFirstName(), body.getFirstName());
        assertEquals(userDto.getLastName(), body.getLastName());
        assertEquals(userDto.getAlias(), body.getAlias());
    }

    @Test
    public void tes_createUser_duplicate() {
        UserProfileDto userDto = createRequestObject();

        var firstPost = this.testRestTemplate.postForEntity(getBasePath().toUriString(), userDto, UserProfileDto.class);

        assertSame(HttpStatus.CREATED, firstPost.getStatusCode());

        var secondPost = this.testRestTemplate.postForEntity(getBasePath().toUriString(), userDto, GenericErrorResponse.class);

        assertSame(HttpStatus.CONFLICT, secondPost.getStatusCode());

        assertEquals(ApiErrorCodeEnum.USER_PROFILE_UNIQUE_IDENTIFIER_CONFLICT.getCode(), secondPost.getBody().getApiErrorCode());
    }

    @ParameterizedTest
    @MethodSource("createUserExceptionalCasesDataSource")
    public void test_createUser_exceptionalCases(UserProfileDto requestBody) {
        var result = this.testRestTemplate.postForEntity(getBasePath().toUriString(), requestBody, UserProfileDto.class);
        assertSame(HttpStatus.BAD_REQUEST, result.getStatusCode());
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

        var postUser = this.testRestTemplate.postForEntity(getBasePath().toUriString(), userDto, UserProfileDto.class);

        assertSame(HttpStatus.CREATED, postUser.getStatusCode());
        var userId = postUser.getBody().getId();
        var newPath = getBasePath()
                .path("/{userId}")
                .uriVariables(Map.of("userId", userId))
                .toUriString();
        var response = this.testRestTemplate.getForEntity(newPath, UserProfileDto.class);

        assertSame(HttpStatus.OK, response.getStatusCode());

        var responseBody = response.getBody();
        assertNotNull(responseBody);

        assertEquals(userDto.getFirstName(), responseBody.getFirstName());
        assertEquals(userDto.getLastName(), responseBody.getLastName());
        assertEquals(userDto.getAlias(), responseBody.getAlias());
    }

    @Test
    public void test_retrieveUser_userNotFound() {
        var newPath = getBasePath()
                .path("/{userId}")
                .uriVariables(Map.of("userId", "123456"))
                .toUriString();
        var response = this.testRestTemplate.getForEntity(newPath, UserProfileDto.class);

        assertSame(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private static UserProfileDto createRequestObject() {
        int current = COUNT.incrementAndGet();
        return new UserProfileDto()
                .setFirstName(TEST_USER_FIRST_NAME + "#" + current)
                .setLastName(TEST_USER_LAST_NAME + "#" + current)
                .setAlias(TEST_USER_ALIAS + "#" + current);
    }

}

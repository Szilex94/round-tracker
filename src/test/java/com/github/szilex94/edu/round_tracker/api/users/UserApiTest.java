package com.github.szilex94.edu.round_tracker.api.users;

import com.github.szilex94.edu.round_tracker.rest.user.profile.UserProfileDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserApiTest {

    private static final String TEST_USER_FIRST_NAME = "testFirstName";

    private static final String TEST_USER_LAST_NAME = "testLastName";

    private static final String TEST_USER_ALIAS = "testAlias";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    public UriComponentsBuilder getBasePath() {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(this.port)
                .path("round-tracker/v1/users/profile");
    }

    @Test
    public void tes_createUser() {
        UserProfileDto userDto = createRequestObject();

        var result = this.testRestTemplate.postForEntity(getBasePath().toUriString(), userDto, UserProfileDto.class);

        assertSame(HttpStatus.OK, result.getStatusCode());
        var body = result.getBody();
        assertNotNull(body);
        assertNotNull(body.getId());
        assertEquals(TEST_USER_FIRST_NAME, body.getFirstName());
        assertEquals(TEST_USER_LAST_NAME, body.getLastName());
        assertEquals(TEST_USER_ALIAS, body.getAlias());
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

        assertSame(HttpStatus.OK, postUser.getStatusCode());
        var userId = postUser.getBody().getId();
        var newPath = getBasePath()
                .path("/{userId}")
                .uriVariables(Map.of("userId", userId))
                .toUriString();
        var response = this.testRestTemplate.getForEntity(newPath, UserProfileDto.class);

        assertSame(HttpStatus.OK, response.getStatusCode());

        var responseBody = response.getBody();
        assertNotNull(responseBody);

        assertEquals(TEST_USER_FIRST_NAME, responseBody.getFirstName());
        assertEquals(TEST_USER_LAST_NAME, responseBody.getLastName());
        assertEquals(TEST_USER_ALIAS, responseBody.getAlias());
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
        return new UserProfileDto()
                .setFirstName(TEST_USER_FIRST_NAME)
                .setLastName(TEST_USER_LAST_NAME)
                .setAlias(TEST_USER_ALIAS);
    }

}

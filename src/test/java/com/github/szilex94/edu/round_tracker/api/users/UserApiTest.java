package com.github.szilex94.edu.round_tracker.api.users;

import com.github.szilex94.edu.round_tracker.rest.ApplicationRequestMappings;
import com.github.szilex94.edu.round_tracker.rest.user.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserApiTest {

    private final String TEST_USER_FIRST_NAME = "testFirstName";

    private final String TEST_USER_LAST_NAME = "testLastName";

    private final String TEST_USER_ALIAS = "testAlias";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String uri;

    @BeforeEach
    public void beforeEach() {
        this.uri = "http://localhost:" + port + "/" + ApplicationRequestMappings.USERS_V1;
    }

    @Test
    public void testCreateUser() {
        UserDto userDto = createRequestObject();

        var result = this.testRestTemplate.postForEntity(this.uri, userDto, UserDto.class);

        assertSame(HttpStatus.OK, result.getStatusCode());
        var body = result.getBody();
        assertNotNull(body);
        assertNotNull(body.getId());
        assertEquals(TEST_USER_FIRST_NAME, body.getFirstName());
        assertEquals(TEST_USER_LAST_NAME, body.getLastName());
        assertEquals(TEST_USER_ALIAS, body.getAlias());

    }

    private UserDto createRequestObject() {
        return new UserDto()
                .setFirstName(TEST_USER_FIRST_NAME)
                .setLastName(TEST_USER_LAST_NAME)
                .setAlias(TEST_USER_ALIAS);
    }

}

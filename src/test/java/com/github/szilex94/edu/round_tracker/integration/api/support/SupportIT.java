package com.github.szilex94.edu.round_tracker.integration.api.support;

import com.github.szilex94.edu.round_tracker.integration.BaseTestContainerIT;
import com.github.szilex94.edu.round_tracker.integration.Endpoints;
import com.github.szilex94.edu.round_tracker.integration.TestRestUtilities;
import com.github.szilex94.edu.round_tracker.rest.support.CaliberTypeDefinitionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SupportIT extends BaseTestContainerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private TestRestUtilities utilities;

    @BeforeEach
    public void beforeEach() {
        this.utilities = new TestRestUtilities(this.port, this.testRestTemplate);
    }

    private UriComponentsBuilder getBasePath() {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(this.port)
                .path(Endpoints.SUPPORT_CALIBER_DEF);
    }

    private static Stream<CaliberTypeDefinitionDto> getInvalidCaliberDefinitionsForCreate() {
        return Stream.<CaliberTypeDefinitionDto>builder()
                .add(new CaliberTypeDefinitionDto(null, "dispName", "desc"))
                .add(new CaliberTypeDefinitionDto("", "dispName", "desc"))
                .add(new CaliberTypeDefinitionDto("   ", "dispName", "desc"))
                .add(new CaliberTypeDefinitionDto("9 mm", "dispName", "desc"))//contains white space
                .add(new CaliberTypeDefinitionDto("somet 223 dd", "dispName", "desc"))//contains multiple white space
                .add(new CaliberTypeDefinitionDto("  well", "dispName", "desc"))// leading white spaces
                .add(new CaliberTypeDefinitionDto("not   ", "dispName", "desc"))//tailing white space
                .add(new CaliberTypeDefinitionDto("\tnot", "dispName", "desc"))//contains tab
                .add(new CaliberTypeDefinitionDto("not \t", "dispName", "desc"))
                .add(new CaliberTypeDefinitionDto("not\r\f", "dispName", "desc"))//carriage return & line feed
                .build();
    }

    @ParameterizedTest
    @MethodSource("getInvalidCaliberDefinitionsForCreate")
    public void test_create_exceptionalCases(CaliberTypeDefinitionDto caliberDef) {
        final var uri = getBasePath().toUriString();
        var response = this.testRestTemplate.postForEntity(uri, caliberDef, CaliberTypeDefinitionDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void test_create_happyFlow() {
        final var uri = getBasePath().toUriString();
        var input = new CaliberTypeDefinitionDto("9mm", "dispName", "desc");
        var response = this.testRestTemplate.postForEntity(uri, input, CaliberTypeDefinitionDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void test_create_duplicateTypeDef() {
        final var uri = getBasePath().toUriString();
        var input = new CaliberTypeDefinitionDto("9mm", "dispName", "desc");
        var response = this.testRestTemplate.postForEntity(uri, input, CaliberTypeDefinitionDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        input = new CaliberTypeDefinitionDto("9mm", "other", "otherDesc");
        response = this.testRestTemplate.postForEntity(uri, input, CaliberTypeDefinitionDto.class);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    //TODO tests for get & patch
}

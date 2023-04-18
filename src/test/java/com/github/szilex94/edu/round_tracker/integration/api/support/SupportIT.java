package com.github.szilex94.edu.round_tracker.integration.api.support;

import com.github.szilex94.edu.round_tracker.integration.BaseTestContainerIT;
import com.github.szilex94.edu.round_tracker.integration.Endpoints;
import com.github.szilex94.edu.round_tracker.integration.RestTestUtilities;
import com.github.szilex94.edu.round_tracker.rest.support.CaliberTypeDefinitionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SupportIT extends BaseTestContainerIT {

    @Autowired
    WebTestClient webTestClient;

    private RestTestUtilities utilities;

    @BeforeEach
    public void beforeEach() {
        this.utilities = new RestTestUtilities(webTestClient);
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
        webTestClient.post()
                .uri(Endpoints.SUPPORT_CALIBER_DEF)
                .bodyValue(caliberDef)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void test_create_happyFlow() {
        var input = new CaliberTypeDefinitionDto("9mm", "displayName", "desc");
        var response = this.utilities.createNewCaliberTypeDefinition(input);

        assertEquals(input.code(), response.code());
        assertEquals(input.displayName(), response.displayName());
        assertEquals(input.description(), response.description());
    }

    @Test
    public void test_create_duplicateTypeDef() {

        var first = this.utilities.createNewCaliberTypeDefinition();
        var second = new CaliberTypeDefinitionDto(first.code(), "other", "otherDesc");

        webTestClient.post()
                .uri(Endpoints.SUPPORT_CALIBER_DEF)
                .bodyValue(second)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);

    }

    @Test
    public void test_retrieve() {

        var definition = this.utilities.createNewCaliberTypeDefinition();


        var response = this.webTestClient.get()
                .uri(Endpoints.SUPPORT_CALIBER_DEF)
                .accept(MediaType.APPLICATION_NDJSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(CaliberTypeDefinitionDto.class);


        Flux<CaliberTypeDefinitionDto> flux = response.getResponseBody()
                .filter(definition::equals);

        StepVerifier.create(flux)
                .expectNext(definition)
                .expectComplete()
                .verify();


    }

    @Test
    public void test_retrieveMultiple() {
        this.utilities.createNewCaliberTypeDefinition();
        Set<CaliberTypeDefinitionDto> createdDefinitions = new HashSet<>();
        createdDefinitions.add(this.utilities.createNewCaliberTypeDefinition());
        createdDefinitions.add(this.utilities.createNewCaliberTypeDefinition());
        createdDefinitions.add(this.utilities.createNewCaliberTypeDefinition());


        var response = this.webTestClient.get()
                .uri(Endpoints.SUPPORT_CALIBER_DEF)
                .accept(MediaType.APPLICATION_NDJSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(CaliberTypeDefinitionDto.class);


        Flux<CaliberTypeDefinitionDto> flux = response.getResponseBody()
                .filter(createdDefinitions::contains);

        StepVerifier.create(flux)
                .expectNextCount(3)
                .expectComplete()
                .verify();


    }

    @Test
    public void test_patch_nonExisting() {

        var nonExisting = new CaliberTypeDefinitionDto("nonExistent",
                "nonExistent",
                "This should not exist!");

        this.webTestClient.patch()
                .uri(Endpoints.SUPPORT_CALIBER_DEF)
                .bodyValue(nonExisting)
                .exchange()
                .expectStatus().isNotFound();

        var existingDefinitions = this.webTestClient.get()
                .uri(Endpoints.SUPPORT_CALIBER_DEF)
                .accept(MediaType.APPLICATION_NDJSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(CaliberTypeDefinitionDto.class);

        Flux<CaliberTypeDefinitionDto> flux = existingDefinitions.getResponseBody()
                .filter(nonExisting::equals);

        StepVerifier.create(flux)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    public void test_patch_validUpdate() {

        var createdDef = this.utilities.createNewCaliberTypeDefinition();

        var modified = new CaliberTypeDefinitionDto(createdDef.code(), "updatedDisplayName", "Updated Description!");

        this.webTestClient.patch()
                .uri(Endpoints.SUPPORT_CALIBER_DEF)
                .bodyValue(modified)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CaliberTypeDefinitionDto.class)
                .isEqualTo(modified);

        var existingDefinitions = this.webTestClient.get()
                .uri(Endpoints.SUPPORT_CALIBER_DEF)
                .accept(MediaType.APPLICATION_NDJSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(CaliberTypeDefinitionDto.class);


        Flux<CaliberTypeDefinitionDto> flux = existingDefinitions.getResponseBody()
                .filter(current -> createdDef.code().equals(current.code()));

        StepVerifier.create(flux)
                .expectNext(modified)
                .expectComplete()
                .verify();
    }
}

package com.github.szilex94.edu.round_tracker.integration.api.tracking;

import com.github.szilex94.edu.round_tracker.integration.BaseTestContainerIT;
import com.github.szilex94.edu.round_tracker.integration.Endpoints;
import com.github.szilex94.edu.round_tracker.integration.RestTestUtilities;
import com.github.szilex94.edu.round_tracker.rest.error.ApiErrorCodeEnum;
import com.github.szilex94.edu.round_tracker.rest.error.GenericErrorResponse;
import com.github.szilex94.edu.round_tracker.rest.support.CaliberTypeDefinitionDto;
import com.github.szilex94.edu.round_tracker.rest.tracking.model.AmmunitionChangeDto;
import com.github.szilex94.edu.round_tracker.rest.tracking.model.AmmunitionSummaryDto;
import com.github.szilex94.edu.round_tracker.rest.user.profile.UserProfileDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TrackingIT extends BaseTestContainerIT {

    @Autowired
    private WebTestClient webTestClient;

    private RestTestUtilities utilities;

    private UserProfileDto profileDto;

    private CaliberTypeDefinitionDto caliberDefinition;

    @BeforeEach
    public void beforeEach() {
        this.utilities = new RestTestUtilities(webTestClient);
        this.profileDto = utilities.createNewUser();
        this.caliberDefinition = utilities.createNewCaliberTypeDefinition();
    }


    private static Stream<AmmunitionChangeDto> invalidAmmunitionChangeSource() {
        return Stream.<AmmunitionChangeDto>builder()
                .add(new AmmunitionChangeDto(null, null, 1))//Null ammunitionCode
                .add(new AmmunitionChangeDto(null, "", 1))//Empty AmmunitionCode
                .add(new AmmunitionChangeDto(null, "hasText", 0))//Amount is zero
                .build();
    }

    @ParameterizedTest
    @MethodSource("invalidAmmunitionChangeSource")
    public void test_trackChange_testStaticValidation(AmmunitionChangeDto dto) {
        postRequestSpec().bodyValue(dto)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void test_trackChange_nonExistentCode() {

        var body = postRequestSpec().bodyValue(new AmmunitionChangeDto(null, "nonExistent", 10))
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(GenericErrorResponse.class)
                .returnResult().getResponseBody();

        assertEquals(ApiErrorCodeEnum.UNKNOWN_AMMUNITION_CODE.getCode(), body.getApiErrorCode());
    }


    /**
     * @return a request spec which has the URI configured
     */
    private WebTestClient.RequestBodySpec postRequestSpec() {
        return webTestClient.post()
                .uri(Endpoints.TRACKING, Map.of("userId", this.profileDto.getId()));
    }

    @Test
    public void test_recordChange_happyFlow() {
        final var code = caliberDefinition.code();

        var request = new AmmunitionChangeDto(null, code, 10);

        var response = postRequestSpec().bodyValue(request)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AmmunitionSummaryDto.class)
                .returnResult()
                .getResponseBody();

        assertEquals(this.profileDto.getId(), response.userId());

        var summaryMap = response.codeToSummary();
        assertEquals(1, summaryMap.size());

        var entry = summaryMap.get(code);
        assertEquals(10, entry.grandTotal());
        assertNotNull(entry.lastChangeRecordedAt());
    }

    @Test
    public void test_recordChange_unknownCode() {
        final var code = caliberDefinition.code();

        var request = new AmmunitionChangeDto(null, "randomStuff", 10);

        postRequestSpec().bodyValue(request)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }


    @Test
    public void test_recordChange_multipleReplenishments() {
        final var code = caliberDefinition.code();

        var request = new AmmunitionChangeDto(null, code, 10);

        var response = postRequestSpec().bodyValue(request)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AmmunitionSummaryDto.class)
                .returnResult()
                .getResponseBody();

        assertEquals(this.profileDto.getId(), response.userId());

        var summaryMap = response.codeToSummary();
        assertEquals(1, summaryMap.size());

        var firstEntry = summaryMap.get(code);
        assertEquals(10, firstEntry.grandTotal());
        assertNotNull(firstEntry.lastChangeRecordedAt());


        var secondResponse = postRequestSpec().bodyValue(request)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AmmunitionSummaryDto.class)
                .returnResult()
                .getResponseBody();

        assertEquals(this.profileDto.getId(), response.userId());

        var secondMap = secondResponse.codeToSummary();
        assertEquals(1, secondMap.size());

        var secondEntry = secondMap.get(code);
        assertEquals(20, secondEntry.grandTotal());
        assertNotNull(secondEntry.lastChangeRecordedAt());

        assertTrue(secondEntry.lastChangeRecordedAt().isAfter(firstEntry.lastChangeRecordedAt()));
    }

    @Test
    public void test_recordChange_differentCodes() {
        final var code = caliberDefinition.code();

        var request = new AmmunitionChangeDto(null, code, 10);

        var response = postRequestSpec().bodyValue(request)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AmmunitionSummaryDto.class)
                .returnResult()
                .getResponseBody();

        assertEquals(this.profileDto.getId(), response.userId());

        var summaryMap = response.codeToSummary();
        assertEquals(1, summaryMap.size());

        var firstEntry = summaryMap.get(code);
        assertEquals(10, firstEntry.grandTotal());
        assertNotNull(firstEntry.lastChangeRecordedAt());

        var secondDef = this.utilities.createNewCaliberTypeDefinition();
        var secondRequest = new AmmunitionChangeDto(null, secondDef.code(), 5);


        var secondResponse = postRequestSpec().bodyValue(secondRequest)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AmmunitionSummaryDto.class)
                .returnResult()
                .getResponseBody();

        assertEquals(this.profileDto.getId(), response.userId());

        var secondMap = secondResponse.codeToSummary();
        assertEquals(2, secondMap.size());

        //At this stage the map should contain two distinct entries for the two caliber codes
        var initialCodeValue = secondMap.get(code);
        assertEquals(10, initialCodeValue.grandTotal());

        var newlyAddedCodeValue = secondMap.get(secondDef.code());
        assertEquals(5, newlyAddedCodeValue.grandTotal());

    }

    @Test
    public void test_recordChange_trackExpense() {
        final var code = caliberDefinition.code();

        var request = new AmmunitionChangeDto(null, code, 10);

        var response = postRequestSpec().bodyValue(request)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AmmunitionSummaryDto.class)
                .returnResult()
                .getResponseBody();


        var initialTotal = response.codeToSummary().get(code).grandTotal();

        assertEquals(10, initialTotal);

        var secondRequest = new AmmunitionChangeDto(null, code, -5);
        var secondResponse = postRequestSpec().bodyValue(secondRequest)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AmmunitionSummaryDto.class)
                .returnResult()
                .getResponseBody();


        var afterUpdateTotal = secondResponse.codeToSummary().get(code).grandTotal();
        assertEquals(5, afterUpdateTotal);
    }

    @Test
    public void test_recordChange_chainedExpenses() {
        final var code = caliberDefinition.code();

        var request = new AmmunitionChangeDto(null, code, 10);

        var response = postRequestSpec().bodyValue(request)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AmmunitionSummaryDto.class)
                .returnResult()
                .getResponseBody();


        var initialTotal = response.codeToSummary().get(code).grandTotal();

        assertEquals(10, initialTotal);

        var secondRequest = new AmmunitionChangeDto(null, code, -5);
        postRequestSpec().bodyValue(secondRequest)
                .exchange()
                .expectStatus()
                .isOk();
        postRequestSpec().bodyValue(secondRequest)
                .exchange()
                .expectStatus()
                .isOk();
        var finalResponse = postRequestSpec().bodyValue(secondRequest)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AmmunitionSummaryDto.class)
                .returnResult()
                .getResponseBody();


        var afterUpdateTotal = finalResponse.codeToSummary().get(code).grandTotal();
        assertEquals(-5, afterUpdateTotal);
    }

    @Test
    public void test_recordChange_replenishmentAfterExpenses() {
        final var code = caliberDefinition.code();

        var request = new AmmunitionChangeDto(null, code, 10);

        var response = postRequestSpec().bodyValue(request)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AmmunitionSummaryDto.class)
                .returnResult()
                .getResponseBody();


        var initialTotal = response.codeToSummary().get(code).grandTotal();

        assertEquals(10, initialTotal);

        var secondRequest = new AmmunitionChangeDto(null, code, -5);
        postRequestSpec().bodyValue(secondRequest)
                .exchange()
                .expectStatus()
                .isOk();
        postRequestSpec().bodyValue(secondRequest)
                .exchange()
                .expectStatus()
                .isOk();
        var finalExpenseResponse = postRequestSpec().bodyValue(secondRequest)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AmmunitionSummaryDto.class)
                .returnResult()
                .getResponseBody();
        var afterUpdateTotal = finalExpenseResponse.codeToSummary().get(code).grandTotal();
        assertEquals(-5, afterUpdateTotal);

        var replenishment = new AmmunitionChangeDto(null, code, 10);
        var afterAddition = postRequestSpec().bodyValue(replenishment)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AmmunitionSummaryDto.class)
                .returnResult()
                .getResponseBody();
        var afterAdditionTotal = afterAddition.codeToSummary().get(code).grandTotal();
        assertEquals(5, afterAdditionTotal);
    }

}

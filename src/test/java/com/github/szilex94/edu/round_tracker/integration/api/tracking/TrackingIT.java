package com.github.szilex94.edu.round_tracker.integration.api.tracking;

import com.github.szilex94.edu.round_tracker.integration.BaseTestContainerIT;
import com.github.szilex94.edu.round_tracker.integration.Endpoints;
import com.github.szilex94.edu.round_tracker.integration.RestTestUtilities;
import com.github.szilex94.edu.round_tracker.rest.tracking.model.AmmunitionChangeDto;
import com.github.szilex94.edu.round_tracker.rest.tracking.model.UserAmmunitionSummaryDto;
import com.github.szilex94.edu.round_tracker.rest.user.profile.UserProfileDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TrackingIT extends BaseTestContainerIT {

    @LocalServerPort
    @Deprecated
    private int port;

    @Autowired
    @Deprecated
    private TestRestTemplate testRestTemplate;

    @Autowired
    private WebTestClient webTestClient;

    private RestTestUtilities utilities;

    private UserProfileDto profileDto;

    @BeforeEach
    public void beforeEach() {
        this.utilities = new RestTestUtilities(webTestClient);
        this.profileDto = utilities.createNewUser();
    }

    private UriComponentsBuilder getBasePath() {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(this.port)
                .path(Endpoints.TRACKING)
                .uriVariables(Map.of("userId", profileDto.getId()));
    }
//TODO revisit exceptional cases
//    @ParameterizedTest
//    @MethodSource("trackingExceptionalCases")
//    public void test_tracking_exceptionalCases(AmmunitionChangeDto invalidData) {
//
//        var response = this.testRestTemplate.postForEntity(getBasePath().toUriString(),
//                invalidData,
//                UserAmmunitionSummaryDto.class);
//
//        assertSame(HttpStatus.BAD_REQUEST, response.getStatusCode());
//    }
//
//
//    private static Stream<AmmunitionChangeDto> trackingExceptionalCases() {
//        return Stream.<AmmunitionChangeDto>builder()
//                //Zero as amount
//                .add(new AmmunitionChangeDto(null, 0, ChangeTypeDto.EXPENSE, AmmunitionTypeDto.NINE_MILLIMETER))
//                //Missing change type
//                .add(new AmmunitionChangeDto(null, 10, null, AmmunitionTypeDto.NINE_MILLIMETER))
//                //Missing Ammunition Type
//                .add(new AmmunitionChangeDto(null, 10, ChangeTypeDto.EXPENSE, null))
//                //Unknown Ammunition type
//                .add(new AmmunitionChangeDto(null, 10, ChangeTypeDto.EXPENSE, AmmunitionTypeDto.UNKNOWN))
//                .build();
//    }

    @Test
    public void test_trackChange_recordReplenishment_happyFlow() {
        var expense = new AmmunitionChangeDto(null,
                10);

        var response = this.testRestTemplate.postForEntity(getBasePath().toUriString(), expense, UserAmmunitionSummaryDto.class);

        assertSame(HttpStatus.OK, response.getStatusCode());

        var body = response.getBody();
        assertNotNull(body);
        assertEquals(this.profileDto.getId(), body.userId());

        assertEquals(10, body.grandTotal());
    }

    @Test
    public void test_trackChange_expense_happyFlow() {
        var expense = new AmmunitionChangeDto(null,
                -10);

        var response = this.testRestTemplate.postForEntity(getBasePath().toUriString(), expense, UserAmmunitionSummaryDto.class);

        assertSame(HttpStatus.OK, response.getStatusCode());

        var body = response.getBody();
        assertNotNull(body);
        assertEquals(this.profileDto.getId(), body.userId());

        assertEquals(-10, body.grandTotal());
    }

    @Test
    public void test_trackChange_additionThenExpense() {

        var addition = new AmmunitionChangeDto(null,
                10);

        var response = this.testRestTemplate.postForEntity(getBasePath().toUriString(), addition, UserAmmunitionSummaryDto.class);

        assertSame(HttpStatus.OK, response.getStatusCode());

        var body = response.getBody();
        assertEquals(10, body.grandTotal());

        var expense = new AmmunitionChangeDto(null,
                -10);

        response = this.testRestTemplate.postForEntity(getBasePath().toUriString(), expense, UserAmmunitionSummaryDto.class);

        assertSame(HttpStatus.OK, response.getStatusCode());

        body = response.getBody();
        assertNotNull(body);
        assertEquals(this.profileDto.getId(), body.userId());

        assertEquals(0, body.grandTotal());
    }

    //TODO add test for multiple chained expenses

    //TODO add test for chained expense and addition

    //TODO add test for correction
}

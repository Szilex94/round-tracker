package com.github.szilex94.edu.round_tracker.integration.api.tracking;

import com.github.szilex94.edu.round_tracker.integration.BaseTestContainerIT;
import com.github.szilex94.edu.round_tracker.integration.Endpoints;
import com.github.szilex94.edu.round_tracker.integration.TestRestUtilities;
import com.github.szilex94.edu.round_tracker.rest.tracking.model.AmmunitionChangeDto;
import com.github.szilex94.edu.round_tracker.rest.tracking.model.AmmunitionTypeDto;
import com.github.szilex94.edu.round_tracker.rest.tracking.model.ChangeTypeDto;
import com.github.szilex94.edu.round_tracker.rest.tracking.model.UserAmmunitionSummaryDto;
import com.github.szilex94.edu.round_tracker.rest.user.profile.UserProfileDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TrackingIT extends BaseTestContainerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private TestRestUtilities utilities;

    private UserProfileDto profileDto;

    @BeforeEach
    public void beforeEach() {
        this.utilities = new TestRestUtilities(this.port, this.testRestTemplate);
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

    @Test
    public void happyFlow_recordExpense() {
        var expense = new AmmunitionChangeDto(null,
                10,
                ChangeTypeDto.EXPENSE,
                AmmunitionTypeDto.NINE_MILLIMETER);

        var response = this.testRestTemplate.postForEntity(getBasePath().toUriString(), expense, UserAmmunitionSummaryDto.class);

        assertSame(HttpStatus.OK, response.getStatusCode());

        var body = response.getBody();
        assertNotNull(body);
        assertEquals(this.profileDto.getId(), body.userId());

        var typeToCount = body.typeToCount();
        assertEquals(1, typeToCount.size(), "Expected summary count exceeded!");
        assertEquals(10, typeToCount.get(AmmunitionTypeDto.NINE_MILLIMETER));
    }
}

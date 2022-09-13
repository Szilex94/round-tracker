package com.github.szilex94.edu.round_tracker.service.user.profile;

import lombok.Data;

@Data
public class UserProfile {

    /**
     * unique id used by the DB
     */
    private String id;

    private String profileId;

    private String firstName;

    private String lastName;

    private String alias;

}

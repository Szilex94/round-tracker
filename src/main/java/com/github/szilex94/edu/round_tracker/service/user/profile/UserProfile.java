package com.github.szilex94.edu.round_tracker.service.user.profile;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserProfile {

    /**
     * unique id used by the DB
     */
    private String id;

    private String firstName;

    private String lastName;

    private String alias;

}

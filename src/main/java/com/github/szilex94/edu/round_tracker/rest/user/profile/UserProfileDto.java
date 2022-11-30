package com.github.szilex94.edu.round_tracker.rest.user.profile;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserProfileDto {

    private String id;

    @NotEmpty(message = "Null or empty first name not allowed!")
    private String firstName;

    @NotEmpty(message = "Null or empty last name not allowed!")
    private String lastName;

    private String alias;

}

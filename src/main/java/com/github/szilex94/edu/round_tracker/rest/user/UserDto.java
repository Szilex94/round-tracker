package com.github.szilex94.edu.round_tracker.rest.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDto {

    @NotEmpty(message = "Null or empty first name not allowed!")
    private String firstName;

    @NotEmpty(message = "Null or empty last name not allowed!")
    private String lastName;

    private String alias;

}

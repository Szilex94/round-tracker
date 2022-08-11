package com.github.szilex94.edu.round_tracker.repository.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class UserDao {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String alias;
}

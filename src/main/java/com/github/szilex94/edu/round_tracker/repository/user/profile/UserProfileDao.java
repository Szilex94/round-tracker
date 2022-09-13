package com.github.szilex94.edu.round_tracker.repository.user.profile;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document(collection = "user.profile")
public class UserProfileDao {

    @Id
    private String id;

    @Indexed(unique = true)
    private String profileId;

    private String firstName;

    private String lastName;

    private String alias;
}

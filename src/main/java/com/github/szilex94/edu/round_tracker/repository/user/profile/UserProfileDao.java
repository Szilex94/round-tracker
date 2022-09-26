package com.github.szilex94.edu.round_tracker.repository.user.profile;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document(collection = "user.profile")
@CompoundIndex(name = "unique-profile-identifier",
        def = "{'firstName': 1, 'lastName': 1, 'alias': 1}",
        unique = true)
public class UserProfileDao {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String alias;
}

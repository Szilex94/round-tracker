package com.github.szilex94.edu.round_tracker.repository.support.caliber.mongo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document(collection = "support.caliber")
public class CaliberTypeDefinitionDao {

    /**
     * FIELD_CALIBER_CODE - represents the {@link #code} field which can be used when building DSL queries
     */
    public static final String FIELD_CALIBER_CODE = "code";

    @Id
    private String code;

    private String displayName;

    private String description;
}

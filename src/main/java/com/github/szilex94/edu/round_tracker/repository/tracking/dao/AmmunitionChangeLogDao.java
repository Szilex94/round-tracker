package com.github.szilex94.edu.round_tracker.repository.tracking.dao;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class AmmunitionChangeLogDao {

    private String id;

    private String userId;

    private ChangeTypeDao changeType;

    private int amount;

}

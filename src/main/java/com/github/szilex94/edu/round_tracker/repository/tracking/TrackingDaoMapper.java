package com.github.szilex94.edu.round_tracker.repository.tracking;

import com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionChangeLogDao;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChange;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChangeLog;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrackingDaoMapper {

    AmmunitionChangeLogDao fromAmmunitionChange(AmmunitionChange change);

    AmmunitionChangeLog fromDao(AmmunitionChangeLogDao dao);

}

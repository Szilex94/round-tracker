package com.github.szilex94.edu.round_tracker.repository.tracking.mapper;

import com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionChangeLogDao;
import com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionSummaryDao;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChange;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChangeSummary;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrackingDaoMapper {

    AmmunitionChangeLogDao fromAmmunitionChange(AmmunitionChange change);

    default AmmunitionChangeSummary toAmmunitionChange(AmmunitionSummaryDao dao) {
        var result = this.builderWithCoreFields(dao);

        for (var current : dao.entries().entrySet()) {
            var value = current.getValue();
            result.newSummaryEntryForCode(current.getKey())
                    .setGrandTotal(value.grandTotal())
                    .setLastChangeRecordedAt(value.lastRecordedChange())
                    .buildAndReturnToParent();
        }

        return result.build();
    }

    AmmunitionChangeSummary.Builder builderWithCoreFields(AmmunitionSummaryDao dao);

}

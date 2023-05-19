package com.github.szilex94.edu.round_tracker.repository.tracking.repository;

import com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionChangeLogDao;

import static com.google.common.base.Preconditions.checkArgument;

public final class ArchiveTransferFailedException extends RuntimeException {

    private final AmmunitionChangeLogDao failedEntity;

    ArchiveTransferFailedException(String message, AmmunitionChangeLogDao failedEntity) {
        super(message);
        this.failedEntity = failedEntity;
    }

    public static ArchiveTransferFailedException forEntity(AmmunitionChangeLogDao failedEntity) {
        checkArgument(failedEntity != null, "Null failed entity not allowed!");
        return new ArchiveTransferFailedException("Failed to transfer entity '" + failedEntity.getId() + "' into LTS collection!", failedEntity);
    }

    public String getId() {
        return failedEntity.getId();
    }

}

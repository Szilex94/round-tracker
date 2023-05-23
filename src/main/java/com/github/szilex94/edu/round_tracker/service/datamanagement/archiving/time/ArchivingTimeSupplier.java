package com.github.szilex94.edu.round_tracker.service.datamanagement.archiving.time;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.function.Supplier;

/**
 * Implementations compute cutoff time for the archiving process.
 *
 * @author szilex94
 */
public interface ArchivingTimeSupplier extends Supplier<LocalDate> {

    /**
     * Simplest implementation which will indicate that everything should be archived
     *
     * @return the present time (i.e. {@link OffsetDateTime#now()}) as cutoff
     */
    static ArchivingTimeSupplier withPresentDateAsCutoff() {
        return LocalDate::now;
    }
}

package com.github.szilex94.edu.round_tracker.service.datamanagement.archiving;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

public record ArchivingSubmissionRequest(
        boolean accepted,
        Optional<String> detail
) {
    public ArchivingSubmissionRequest(boolean accepted) {
        this(accepted, Optional.empty());
    }

    public ArchivingSubmissionRequest(boolean accepted, String detail) {
        this(accepted, checkAndWrap(detail));
    }

    public ArchivingSubmissionRequest {
        checkArgument(detail != null, "Null detail optional not allowed!");
    }

    private static Optional<String> checkAndWrap(String detail) {
        checkArgument(detail != null, "Null detail not allowed!");
        checkArgument(!isNullOrEmpty(detail), "Empty detail not allowed!");
        return Optional.of(detail);
    }


}

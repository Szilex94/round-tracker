package com.github.szilex94.edu.round_tracker.rest.error;

import com.github.szilex94.edu.round_tracker.rest.error.codes.ApiErrorDetail;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Small utility class which should aid in processing Error messages
 *
 * @author szilex94
 */
public final class ErrorHandlingUtils {

    private ErrorHandlingUtils() {
        //suppress default constructor
    }

    /**
     * Creates a new {@link ProblemDetail} using the supplied status code and transfers the data from {@link ApiErrorDetail}
     *
     * @param statusCode   non-null {@link HttpStatusCode}
     * @param errorDetails non-null {@link ApiErrorDetail}
     * @return a new {@link ProblemDetail} with the supplied status code and details
     * @throws IllegalArgumentException for {@code null} inputs
     * @see #transferDetails(ApiErrorDetail, ProblemDetail)
     */
    public static ProblemDetail createNew(HttpStatusCode statusCode, ApiErrorDetail errorDetails) {
        var result = ProblemDetail.forStatus(statusCode);
        return transferDetails(errorDetails, result);
    }

    /**
     * Transfers the following information into the supplied {@link ProblemDetail} instance:
     * <ul>
     *     <li>title</li>
     *     <li>apiErrorCode (as property using {@link ApiErrorDetail#API_ERROR_CODE} as key)</li>
     * </ul>
     *
     * @param source non-null {@link ApiErrorDetail} from which relevant information is taken
     * @param target non-null {@link ProblemDetail} into which relevant information is transferred
     * @return the supplied {@link ProblemDetail}
     * @throws IllegalArgumentException for {@code null} inputs
     */
    public static ProblemDetail transferDetails(ApiErrorDetail source, ProblemDetail target) {
        checkArgument(source != null, "Null apiErrorDetails not allowed!");
        checkArgument(target != null, "Null problemDetail not allowed!");

        target.setTitle(source.getTitle());
        target.setProperty(ApiErrorDetail.API_ERROR_CODE, source.getApiErrorCode());

        return target;
    }
}

package com.github.szilex94.edu.round_tracker.rest.error;

import com.github.szilex94.edu.round_tracker.rest.error.codes.ApiErrorDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Support class meant to ease & standardize the creation of a {@link org.springframework.http.ProblemDetail} instance.
 * <p>
 * <b>Note:</b> This component generates URIs (used as the type of the resulting detail, which is set via {@link ProblemDetail#setType(URI)}).
 * These URIs point towards gitHub wiki and to ensure proper functioning of the generated  URIs each {@link ApiErrorDetail}
 * must have a dedicated chapter on the wiki page!
 *
 * @author szilex94
 */
@Component
public class ProblemDetailFactory {

    public static final String API_ERROR_CODE = "apiErrorCode";

    public static final String TIME_STAMP = "timeStamp";

    private final URI baseWikiURI;

    public ProblemDetailFactory(@Value("${app.documentation.wiki.error-page}") String baseWikiURI) {
        checkArgument(!isNullOrEmpty(baseWikiURI), "Null or empty wiki URI not allowed!");
        this.baseWikiURI = URI.create(baseWikiURI);
    }

    /**
     * Creates a new Problem details and fills out the following details:
     * <ul>
     *     <li><b>type</b> - will create an URI which redirects to the gitHub wiki. It appends {@link ApiErrorDetail#getApiErrorCode()}
     *     to the end of the URI to point the browser to the appropriate section</li>
     *     <li><b>title</b> - taken from the supplied errorDetail</li>
     *     <li><b>apiErrorCode</b> - appended as meta-information (using property key {@link #API_ERROR_CODE}) and will contain the errorCode</li>
     *     <li><b>timeStamp</b> - ISO-8601 time stamp of the current time appended as an additional property (using property key {@link #TIME_STAMP})</li>
     * </ul>
     *
     * @param statusCode  non-null {@link HttpStatusCode}
     * @param errorDetail non-null {@link ApiErrorDetail}
     * @return a new {@link ProblemDetail} based on the supplied input
     * @throws IllegalArgumentException for null inputs
     */
    public ProblemDetail createAndInitiate(HttpStatusCode statusCode, ApiErrorDetail errorDetail) {
        checkArgument(statusCode != null, "Null status code not allowed!");
        checkArgument(errorDetail != null, "Null errorDetail not allowed!");

        ProblemDetail result = ProblemDetail.forStatus(statusCode);

        var wikiURI = UriComponentsBuilder.fromUri(baseWikiURI)
                .fragment(errorDetail.getApiErrorCode())
                .build()
                .toUri();

        result.setType(wikiURI);
        result.setTitle(errorDetail.getTitle());
        result.setProperty(API_ERROR_CODE, errorDetail.getApiErrorCode());
        result.setProperty(TIME_STAMP, DateTimeFormatter.ISO_DATE_TIME.format(OffsetDateTime.now()));

        return result;
    }

}

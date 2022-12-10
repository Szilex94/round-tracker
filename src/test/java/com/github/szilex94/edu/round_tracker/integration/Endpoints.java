package com.github.szilex94.edu.round_tracker.integration;

/**
 * Convenient place which contains all endpoints subject to testing
 *
 * @author szilex94
 */
public final class Endpoints {

    public static final String USER_PROFILE = "round-tracker/v1/user/profile";

    /**
     * TRACKING - has URI variable <b>{@code userId}</b>
     */
    public static final String TRACKING = "round-tracker/v1/user/{userId}/tracking";

    private Endpoints() {
        //Suppress default constructor
    }
}

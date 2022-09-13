package com.github.szilex94.edu.round_tracker.service.user.profile;

import java.util.function.Function;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Generates a human readable yet unique id for the supplied profile
 */
public interface ProfileIdGenerator extends Function<UserProfile, String> {

    static ProfileIdGenerator defaultImpl() {
        return InterfaceSupport::joinCoreFieldsWithDots;
    }

    /**
     * @param profile non null argument
     * @return a string which is unique to the supplied profile
     * @implSpec in order to ensure consistency all implementations must only use attributes publicly exposed in the
     * supplied {@link UserProfile} instance excluding the {@link UserProfile#getId()} which is offered by the DB and not
     * known in advance
     */
    @Override
    String apply(UserProfile profile);
}

class InterfaceSupport {

    static String joinCoreFieldsWithDots(UserProfile up) {
        checkArgument(up != null, "Null input not allowed!");
        String firstName = up.getFirstName();
        String lastName = up.getLastName();

        checkArgument(!isNullOrEmpty(firstName), "Null or empty first name not allowed!");
        checkArgument(!isNullOrEmpty(lastName), "Null or empty last name not allowed!");

        StringBuilder sb = new StringBuilder()
                .append(firstName)
                .append('.')
                .append(lastName);

        //Alias is considered optional, only append it if available
        String alias = up.getAlias();
        if (!isNullOrEmpty(alias)) {
            sb.append('.').append(alias);
        }
        return sb.toString();

    }

}
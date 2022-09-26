package com.github.szilex94.edu.round_tracker.rest.user.profile;

import com.github.szilex94.edu.round_tracker.service.user.profile.UserProfile;
import com.github.szilex94.edu.round_tracker.service.user.profile.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("round-tracker/v1/users/profile")
public class UserProfileController {

    private final UserProfileDtoMapper userMapper;

    private final UserProfileService userService;

    @Autowired
    public UserProfileController(UserProfileDtoMapper userMapper, UserProfileService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserProfileDto> createUser(@Valid @RequestBody UserProfileDto userDto) {
        UserProfile user = userMapper.fromDto(userDto);
        return userService.createNewUser(user)
                .map(userMapper::toDto);
    }

    @GetMapping(path = "{userId}", produces = APPLICATION_JSON_VALUE)
    public Mono<UserProfileDto> getUser(@PathVariable String userId) {
        return userService.retrieveById(userId)
                .switchIfEmpty(Mono.error(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(userMapper::toDto);
    }

    @PatchMapping(path = "{userId}", produces = APPLICATION_JSON_VALUE)
    public Mono<UserProfileDto> patchUserProfile(@PathVariable String userId,
                                                 @RequestBody PatchUserProfileDto patchUserProfile) {
        UserProfile userProfile = userMapper.fromPathDto(patchUserProfile)
                .setId(userId);

        return userService.updateUserProfile(userProfile)
                .switchIfEmpty(Mono.error(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(userMapper::toDto);
    }


}

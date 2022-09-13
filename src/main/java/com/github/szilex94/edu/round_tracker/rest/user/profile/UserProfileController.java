package com.github.szilex94.edu.round_tracker.rest.user.profile;

import com.github.szilex94.edu.round_tracker.mappers.UserMapper;
import com.github.szilex94.edu.round_tracker.service.user.profile.User;
import com.github.szilex94.edu.round_tracker.service.user.profile.UserService;
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

    private final UserMapper userMapper;

    private final UserService userService;

    @Autowired
    public UserProfileController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Mono<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        User user = userMapper.fromDto(userDto);
        return userService.createNewUser(user)
                .map(userMapper::toDto);
    }

    @GetMapping(path = "{userId}", produces = APPLICATION_JSON_VALUE)
    public Mono<UserDto> getUser(@PathVariable String userId) {
        return userService.retrieveById(userId)
                .switchIfEmpty(Mono.error(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(userMapper::toDto);
    }

}

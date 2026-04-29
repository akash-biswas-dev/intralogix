package com.biswasakashdev.nexussphere.users.controller;


import com.biswasakashdev.nexussphere.common.response.UserResponse;
import com.biswasakashdev.nexussphere.users.dtos.requests.UserProfileRequest;
import com.biswasakashdev.nexussphere.users.dtos.response.UserProfileResponse;
import com.biswasakashdev.nexussphere.users.models.Users;
import com.biswasakashdev.nexussphere.users.services.UserService;
import com.biswasakashdev.nexussphere.users.utils.UsersUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;


    //    Returns profile if profile completed otherwise return 404.
    @GetMapping
    public Mono<UserResponse> getUser(
            @RequestHeader(name = "Authentication-Info") String userId
    ) {
        Mono<Users> usersMono = userService.findUserById(userId);
        return usersMono.map(UsersUtils::getUserResponse);
    }


    @GetMapping("/profile")
    public Mono<UserProfileResponse> getUserProfile(
            @RequestHeader(name = "Authentication-Info") String userId
    ){
        Mono<Users> usersMono = userService.findUserById(userId);
        return usersMono.map(UsersUtils::getUserProfileResponse);
    }


    //    Update the profile.
    @PutMapping(value = "/profile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> updateProfile(
            @RequestHeader(name = "Authentication-Info") String userId,
            @RequestBody UserProfileRequest userProfileRequest
    ) {
        Mono<Users> usersMono = userService.updateUserProfile(userId, userProfileRequest);
        return usersMono.then();
    }

}

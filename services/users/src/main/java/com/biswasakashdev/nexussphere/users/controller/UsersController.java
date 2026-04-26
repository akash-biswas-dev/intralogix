package com.biswasakashdev.nexussphere.users.controller;


import com.biswasakashdev.nexussphere.common.dtos.ErrorResponse;
import com.biswasakashdev.nexussphere.common.response.ClientResponse;
import com.biswasakashdev.nexussphere.users.dtos.requests.UserProfileRequest;
import com.biswasakashdev.nexussphere.users.dtos.response.UserProfileResponse;
import com.biswasakashdev.nexussphere.users.models.Users;
import com.biswasakashdev.nexussphere.users.services.UserService;
import com.biswasakashdev.nexussphere.users.utils.UsersUtils;
import com.google.api.Http;
import com.google.rpc.context.AttributeContext;
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
    @GetMapping(value = "/profile")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserProfileResponse> getProfileInfo(
            @RequestHeader(name = "Authentication-Info") String userId
    ) {
        Mono<Users> usersMono = userService.findUserById(userId);
        return usersMono.map(UsersUtils::getUserProfileResponse);
    }


    //    Update the profile.
    @PostMapping(value = "/profile")
    public Mono<ResponseEntity<? extends AttributeContext.Response>> updateProfile(
            @RequestHeader(name = "Authentication-Info") String userId,
            @RequestBody UserProfileRequest userProfileRequest
    ) {
        Mono<Users> usersMono = userService.updateUserProfile(userId, userProfileRequest);


        return null;
    }

}

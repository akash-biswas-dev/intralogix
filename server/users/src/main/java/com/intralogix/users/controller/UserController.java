package com.intralogix.users.controller;


import com.intralogix.users.dtos.response.UserProfileResponse;
import com.intralogix.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    @GetMapping("")
//    public ResponseEntity<PageResponse<UserResponse>> findAllUsers(Integer page , Integer size, Sort){
//        userService.findAllUsers(page,size);
//    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserProfileResponse> getUser(@PathVariable String userId){
        UserProfileResponse userResponse = userService.getUserProfile(userId);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}

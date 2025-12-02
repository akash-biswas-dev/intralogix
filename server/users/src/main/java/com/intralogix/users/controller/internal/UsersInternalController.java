package com.intralogix.users.controller.internal;


import com.intralogix.common.internal.UserIds;
import com.intralogix.common.internal.UserResponseList;
import com.intralogix.common.response.UserResponse;
import com.intralogix.users.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value = "/api/internal/users")
public record UsersInternalController(
        UserService userService
) {

    @PostMapping(value = "/bulk")
    public ResponseEntity<UserResponseList> getAllUsersBulk(@RequestBody UserIds userIds) {
        List<UserResponse> userList = userService.getAllUsersWithId(userIds.userIds());
        return new ResponseEntity<>(new UserResponseList(userList), HttpStatus.OK);
    }
}

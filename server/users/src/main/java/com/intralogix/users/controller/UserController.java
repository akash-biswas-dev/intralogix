package com.intralogix.users.controller;


import com.intralogix.common.response.PageResponse;
import com.intralogix.users.dtos.response.UserProfileResponse;
import com.intralogix.users.dtos.response.UserResponse;
import com.intralogix.users.services.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Getter
    public enum SortParameter{
        JOINED_ON("Joined On", "joinedOn"),;
        private final String value;
        private final String fieldName;
        SortParameter(String value, String fieldName) {
            this.value = value;
            this.fieldName = fieldName;
        }
    }

    @GetMapping(value = "/workspaces/{workspaceId}")
    public ResponseEntity<PageResponse<UserResponse>> getAllUsersForAWorkspace(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page ,
            @RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer size,
            @RequestParam(name = "sortOn", required = false, defaultValue = "JOINED_ON") SortParameter parameter ,
            @RequestParam(name = "direction", required = false, defaultValue = "ASC") Sort.Direction direction){
//        userService.findAllUsers(page,size);
        return null;
    }

    @GetMapping(value = "/sort-parameters")
    public ResponseEntity<List<Map<String,String>>> getAllUsersSortParameters(){
        return ResponseEntity.ok(Arrays.stream(SortParameter.values()).map(parameter -> Map.of(parameter.getValue(),parameter.name())).toList());
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserProfileResponse> getUser(@PathVariable String userId){
        UserProfileResponse userResponse = userService.getUserProfile(userId);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}

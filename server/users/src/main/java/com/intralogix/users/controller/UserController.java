package com.intralogix.users.controller;


import com.intralogix.users.dtos.requests.UserProfileRequest;
import com.intralogix.users.dtos.response.UserProfileResponse;
import com.intralogix.users.services.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
    public enum SortParameter {
        JOINED_ON("Joined On", "joinedOn"),
        ;
        private final String value;
        private final String fieldName;

        SortParameter(String value, String fieldName) {
            this.value = value;
            this.fieldName = fieldName;
        }
    }


    @GetMapping(value = "/sort-parameters")
    public ResponseEntity<List<Map<String, String>>> getAllUsersSortParameters() {
        return ResponseEntity.ok(Arrays.stream(SortParameter.values()).map(parameter -> Map.of(parameter.getValue(), parameter.name())).toList());
    }

}

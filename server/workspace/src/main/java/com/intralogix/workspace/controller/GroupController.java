package com.intralogix.workspace.controller;


import com.intralogix.workspace.dtos.requests.NewGroupRequest;
import com.intralogix.workspace.dtos.response.GroupResponse;
import com.intralogix.workspace.services.GroupsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/groups")
public class GroupController {

    private final GroupsService groupsService;

    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(@RequestBody NewGroupRequest newGroup){
        return ResponseEntity.ok(null);
    }
}

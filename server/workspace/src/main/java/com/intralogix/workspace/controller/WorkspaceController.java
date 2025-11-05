package com.intralogix.workspace.controller;

import com.intralogix.common.response.PageResponse;
import com.intralogix.workspace.dtos.response.WorkspaceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/workspaces")
public class WorkspaceController {

    @GetMapping
    public ResponseEntity<PageResponse<WorkspaceResponse>> getAllWorkspace(@RequestHeader("Authorization") String userId){

        return ResponseEntity.ok(new PageResponse<>(
                1,
                2,
                1,
                true,
                true,
                List.of(new WorkspaceResponse("Workspace 1"), new WorkspaceResponse("Workspace 2"))
        ));
    }
}

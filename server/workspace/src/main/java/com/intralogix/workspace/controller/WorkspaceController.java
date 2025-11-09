package com.intralogix.workspace.controller;

import com.intralogix.common.response.PageResponse;
import com.intralogix.common.response.UserResponse;
import com.intralogix.workspace.dtos.requests.NewWorkspaceRequest;
import com.intralogix.workspace.dtos.response.WorkspaceResponse;
import com.intralogix.workspace.services.WorkspaceService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @Getter
    public enum SortParameter{
        CREATED_ON("Created On", "createdOn"),;
        private final String value;
        private final String fieldName;
        SortParameter(String value, String fieldName) {
            this.value = value;
            this.fieldName = fieldName;
        }
    }

    @GetMapping
    public ResponseEntity<PageResponse<WorkspaceResponse>> getAllWorkspace(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam (name = "page", required = false, defaultValue = "20") Integer page,
            @RequestParam (name = "size", required = false, defaultValue = "20") Integer pageSize,
            @RequestParam (name = "direction", required = false, defaultValue = "ASC")Sort.Direction direction,
            @RequestParam (name = "createdOn", required = false, defaultValue = "CREATED_ON") SortParameter parameter){

        return ResponseEntity.ok(new PageResponse<>(
                1,
                2,
                1,
                true,
                true,
                List.of(new WorkspaceResponse("Workspace 1","user-1", LocalDate.now()),
                        new WorkspaceResponse("Workspace 2","user-2", LocalDate.now()))
        ));
    }

    @GetMapping(value = "exists/{workspaceId}")
    public ResponseEntity<Boolean> getWorkspace(@PathVariable String workspaceId, @RequestHeader("X-User-Id") String userId) {
        Boolean isExists = workspaceService.isWorkspaceNameExists(workspaceId, userId);
        return ResponseEntity.ok(isExists);
    }

    @PostMapping
    public ResponseEntity<WorkspaceResponse> createNewWorkspace(@RequestHeader("X-User-Id") String userId, @RequestBody NewWorkspaceRequest newWorkspace){
        return new ResponseEntity<>(workspaceService.createWorkspace(userId,newWorkspace), HttpStatus.CREATED);
    }

    @GetMapping(value = "{workspaceId}/users")
    public ResponseEntity<PageResponse<UserResponse>> getAllUserWithWorkspace(
            @PathVariable String workspaceId,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page ,
            @RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer size,
            @RequestParam(name = "direction", required = false, defaultValue = "ASC") Sort.Direction direction
    ){
        PageResponse<UserResponse> usersList =  workspaceService.findAllUsersInWorkspace(workspaceId,page,size,direction);
        return ResponseEntity.ok(usersList);
    }
}

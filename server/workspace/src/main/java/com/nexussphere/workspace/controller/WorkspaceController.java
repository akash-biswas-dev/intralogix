package com.nexussphere.workspace.controller;

import com.nexussphere.common.response.ClientResponse;
import com.nexussphere.common.response.PageResponse;
import com.nexussphere.common.response.UserResponse;
import com.nexussphere.workspace.dtos.requests.NewWorkspaceRequest;
import com.nexussphere.workspace.dtos.response.WorkspaceResponse;
import com.nexussphere.workspace.services.WorkspaceService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @Getter
    public enum SortParameter {
        CREATED_ON("Created On", "createdOn"),
        ;
        private final String value;
        private final String fieldName;

        SortParameter(String value, String fieldName) {
            this.value = value;
            this.fieldName = fieldName;
        }
    }


    @PostMapping
    public Mono<ResponseEntity<ClientResponse<Object>>> createNewWorkspace(
            @RequestHeader("Authentication-Info") String userId,
            @RequestBody NewWorkspaceRequest newWorkspace) {
        Mono<Void> isWorkspaceCreated = workspaceService.createWorkspace(userId, newWorkspace);
        return isWorkspaceCreated
                .then(Mono.just(
                        ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(new ClientResponse<>(
                                        true,
                                        null,
                                        null
                                ))
                ))
                .onErrorResume(RuntimeException.class, e -> {
                    ClientResponse<Object> clientResponse = new ClientResponse<>(false,null,e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(clientResponse));
                });
    }

    @GetMapping
    public Mono<ResponseEntity<PageResponse<WorkspaceResponse>>> getAllWorkspace(
            @RequestHeader("Authentication-Info") String userId,
            @RequestParam(name = "page", required = false, defaultValue = "20") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20") Integer pageSize,
            @RequestParam(name = "direction", required = false, defaultValue = "ASC") Sort.Direction direction
    ) {

        return Mono.empty();
    }

    @GetMapping(value = "exists/{workspaceId}")
    public Mono<ResponseEntity<Boolean>> getWorkspace(
            @PathVariable String workspaceId,
            @RequestHeader("Authentication-Info") String userId) {
        return Mono.just(ResponseEntity.ok(true));
    }


    @GetMapping(value = "{workspaceId}/users")
    public Mono<ResponseEntity<PageResponse<UserResponse>>> getAllUserWithWorkspace(
            @PathVariable String workspaceId,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer size,
            @RequestParam(name = "direction", required = false, defaultValue = "ASC") Sort.Direction direction
    ) {
        return Mono.just(ResponseEntity.ok(null));
    }
}

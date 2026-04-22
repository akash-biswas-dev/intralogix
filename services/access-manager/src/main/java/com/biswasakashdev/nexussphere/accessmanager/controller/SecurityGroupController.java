package com.biswasakashdev.nexussphere.accessmanager.controller;


import com.biswasakashdev.nexussphere.accessmanager.dtos.request.SecurityGroupRequest;
import com.biswasakashdev.nexussphere.accessmanager.service.SecurityGroupService;
import com.biswasakashdev.nexussphere.common.response.ClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(name = "/api/v1/security-group")
public class SecurityGroupController {

    private final SecurityGroupService securityGroupService;


    @PostMapping(value = "/{workspaceId}")
    public Mono<ResponseEntity<ClientResponse<?>>> createSecurityGroup(
            @RequestBody SecurityGroupRequest securityGroup,
            @PathVariable String workspaceId,
            @RequestHeader("Authentication-Info") String userId
    ) {

        return Mono.empty();
    }
}

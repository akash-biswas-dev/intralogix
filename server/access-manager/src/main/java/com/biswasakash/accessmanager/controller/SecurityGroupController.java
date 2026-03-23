package com.biswasakash.accessmanager.controller;


import com.biswasakash.accessmanager.dtos.request.SecurityGroupRequest;
import com.biswasakash.accessmanager.service.SecurityGroupService;
import com.nexussphere.common.response.ClientResponse;
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

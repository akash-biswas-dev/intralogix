package com.biswasakash.accessmanager.controller;


import com.nexussphere.common.response.ClientResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(name = "/api/v1/security-group")
public class SecurityGroupController {


    @PostMapping
    public Mono<ResponseEntity<ClientResponse<?>>> createSecurityGroup(
            @RequestBody SecurityGroupRequest securityGroup,
            @PathVariable String workspaceId,
            @RequestHeader("Authentication-Info") String userId
    ) {
        return Mono.empty();
    }
}

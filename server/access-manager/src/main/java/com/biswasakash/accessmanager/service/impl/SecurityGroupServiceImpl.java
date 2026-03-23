package com.biswasakash.accessmanager.service.impl;

import com.biswasakash.accessmanager.dtos.request.EntityIdList;
import com.biswasakash.accessmanager.dtos.request.SecurityGroupRequest;
import com.biswasakash.accessmanager.repository.SecurityGroupRepository;
import com.biswasakash.accessmanager.service.SecurityGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class SecurityGroupServiceImpl implements SecurityGroupService {

    private final SecurityGroupRepository securityGroupRepository;
    private final UsersClient usersClient;

    @Override
    public Mono<Void> createSecurityGroup(String userId, String workspaceId, SecurityGroupRequest securityGroup) {

        return null;
    }

    @Override
    public Mono<Void> addUsersInSecurityGroup(String userId, String workspaceId, String securityGroupId, EntityIdList users) {
        return null;
    }

    @Override
    public Mono<Void> removeUsersInSecurityGroup(String userId, String workspaceId, EntityIdList users) {
        return null;
    }

    @Override
    public Mono<Void> addServiceInSecurityGroup(String userId, String workspaceId, String securityGroupId, EntityIdList services) {
        return null;
    }

    @Override
    public Mono<Void> removeServiceInSecurityGroup(String userId, String workspaceId, String securityGroupId, EntityIdList services) {
        return null;
    }

    @Override
    public Mono<Void> addResourcesInSecurityGroup(String userId, String workspaceId, String securityGroupId, EntityIdList resources) {
        return null;
    }

    @Override
    public Mono<Void> removeResourcesInSecurityGroup(String userId, String workspaceId, String securityGroupId, EntityIdList resources) {
        return null;
    }
}

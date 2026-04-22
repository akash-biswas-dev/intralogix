package com.biswasakashdev.nexussphere.accessmanager.service.impl;

import com.biswasakashdev.nexussphere.accessmanager.dtos.request.EntityIdList;
import com.biswasakashdev.nexussphere.accessmanager.dtos.request.SecurityGroupRequest;
import com.biswasakashdev.nexussphere.accessmanager.repository.SecurityGroupRepository;
import com.biswasakashdev.nexussphere.accessmanager.service.SecurityGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class SecurityGroupServiceImpl implements SecurityGroupService {

    private final SecurityGroupRepository securityGroupRepository;


    @Override
    public Mono<Void> createSecurityGroup(String userId, String workspaceId, SecurityGroupRequest securityGroup) {
        System.out.println("Security group created....");
        return Mono.empty();
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

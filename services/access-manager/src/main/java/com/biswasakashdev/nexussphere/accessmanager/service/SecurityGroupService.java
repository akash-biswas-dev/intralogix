package com.biswasakashdev.nexussphere.accessmanager.service;

import com.biswasakashdev.nexussphere.accessmanager.dtos.request.EntityIdList;
import com.biswasakashdev.nexussphere.accessmanager.dtos.request.SecurityGroupRequest;
import reactor.core.publisher.Mono;

public interface SecurityGroupService {

    Mono<Void> createSecurityGroup(String userId, String workspaceId, SecurityGroupRequest securityGroup);

    Mono<Void> addUsersInSecurityGroup(String userId, String workspaceId, String securityGroupId, EntityIdList users);

    Mono<Void> removeUsersInSecurityGroup(String userId, String workspaceId, EntityIdList users);

    Mono<Void> addServiceInSecurityGroup(String userId, String workspaceId, String securityGroupId, EntityIdList services);

    Mono<Void> removeServiceInSecurityGroup(String userId, String workspaceId, String securityGroupId, EntityIdList services);

    Mono<Void> addResourcesInSecurityGroup(String userId, String workspaceId, String securityGroupId, EntityIdList resources);

    Mono<Void> removeResourcesInSecurityGroup(String userId, String workspaceId, String securityGroupId, EntityIdList resources);
}

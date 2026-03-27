package com.biswasakash.accessmanager.service.impl;

import com.biswasakash.accessmanager.dtos.request.EntityIdList;
import com.biswasakash.accessmanager.dtos.request.SecurityGroupRequest;
import com.biswasakash.accessmanager.models.SecurityGroup;
import com.biswasakash.accessmanager.repository.SecurityGroupRepository;
import com.biswasakash.accessmanager.service.SecurityGroupService;
import com.nexussphere.protocjava.CreateSecurityGroupRequest;
import com.nexussphere.protocjava.CreateSecurityGroupResponse;
import com.nexussphere.protocjava.ReactorAccessManagerGRPCServiceGrpc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;


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

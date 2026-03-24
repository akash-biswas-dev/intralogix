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
public class SecurityGroupServiceImpl extends ReactorAccessManagerGRPCServiceGrpc.AccessManagerGRPCServiceImplBase implements SecurityGroupService {

    private final SecurityGroupRepository securityGroupRepository;

    @Override
    public Mono<CreateSecurityGroupResponse> createSecurityGroup(Mono<CreateSecurityGroupRequest> request) {

        return request.flatMap((createSecurityGroupRequest -> {
            SecurityGroup securityGroup = SecurityGroup.builder()
                    .name("Workspace Admin")
                    .description("This is primary security group for this workspace.")
                    .createdAt(LocalDate.now())
                    .workspaceId(createSecurityGroupRequest.getWorkspaceId())
                    .build();

            return securityGroupRepository
                    .save(securityGroup)
                    .then(Mono.just(
                            CreateSecurityGroupResponse.newBuilder()
                                    .setIsSucceed(true)
                                    .build()
                    ));
        }));
    }

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

package com.biswasakashdev.nexussphere.common.client;

import reactor.core.publisher.Mono;

public interface AccessManagerClient {

    Mono<Boolean> createPrimarySecurityGroup(String userId,String workspaceId);

}

package com.biswasakashdev.nexussphere.common.client;

import reactor.core.publisher.Mono;

public interface AccessManagerClient {

    Mono<Void> createPrimarySecurityGroup(String userId,String workspaceId);

}

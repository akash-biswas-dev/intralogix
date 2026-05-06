package com.biswasakashdev.nexussphere.workspace.config;


import com.biswasakashdev.nexussphere.common.client.AccessManagerClient;
import com.biswasakashdev.nexussphere.common.client.impl.AccessManagerGRPCSecureClient;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
public class GRPCClientConfig {


    @Bean
    AccessManagerClient accessManagerClient(Environment environment) {
        String url = environment.getProperty("services.accessManager");
        if (Objects.isNull(url) || url.isBlank()) {
            throw new IllegalArgumentException("Invalid access manager url.");
        }

        return new AccessManagerGRPCSecureClient(
                ManagedChannelBuilder
                        .forTarget(url)
                        .usePlaintext()
                        .build()
        );
    }

}

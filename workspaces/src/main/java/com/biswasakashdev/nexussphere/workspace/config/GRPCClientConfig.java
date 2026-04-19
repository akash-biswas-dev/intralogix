package com.biswasakashdev.nexussphere.workspace.config;


import java.util.Objects;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.biswasakashdev.nexussphere.common.client.AccessManagerClient;
import com.biswasakashdev.nexussphere.common.client.impl.AccessManagerGRPCClient;

@Configuration
public class GRPCClientConfig {


    @Bean
    AccessManagerClient accessManagerClient(Environment environment){
        String url = environment.getProperty("services.accessManager");
        if(Objects.isNull(url) || url.isBlank()){
            throw new IllegalArgumentException("Invalid access manager url.");
        }
        return new  AccessManagerGRPCClient(url);
    }

}

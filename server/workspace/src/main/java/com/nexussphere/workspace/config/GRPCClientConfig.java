package com.nexussphere.workspace.config;


import com.nexussphere.common.client.AccessManagerClient;
import com.nexussphere.common.client.impl.AccessManagerGRPCClient;
import io.netty.util.internal.StringUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.Objects;

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

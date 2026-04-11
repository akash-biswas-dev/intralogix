package com.biswasakashdev.nexussphere.accessmanager.config;


import com.biswasakashdev.nexussphere.accessmanager.service.grpc.SecurityGroupGRPCService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class GRPCServerConfig {

    private final SecurityGroupGRPCService securityGroupGRPCService;

    @Bean
    Server accessManagerGrpcServer(Environment environment) throws IOException {
        String grpcPort = environment.getProperty("server.grpc");
        if(Objects.isNull(grpcPort) || grpcPort.isBlank()){
            log.error("No GRPC server port provided");
            throw new IllegalArgumentException();
        }
        try{
            int port = Integer.parseInt(grpcPort);

            Server grpcServer = ServerBuilder.forPort(15001)
                    .addService(securityGroupGRPCService)
                    .build()
                    .start();
            log.info("GRPC Server started, listening on {}", port);
            return grpcServer;
        }catch (NumberFormatException e){
            log.error("Invalid GRPC port provided.");
            throw new IllegalStateException();
        }
    }
}

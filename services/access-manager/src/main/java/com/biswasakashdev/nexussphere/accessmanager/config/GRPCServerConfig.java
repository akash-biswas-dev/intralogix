package com.biswasakashdev.nexussphere.accessmanager.config;


import com.biswasakashdev.nexussphere.accessmanager.controller.grpc.SecurityGroupGRPCController;
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

    private final SecurityGroupGRPCController securityGroupGRPCController;

    @Bean
    Server accessManagerGrpcServer(Environment environment) throws IOException {
        String grpcPort = environment.getProperty("server.port");
        if(Objects.isNull(grpcPort) || grpcPort.isBlank()){
            log.error("No GRPC server port provided");
            throw new IllegalArgumentException();
        }
        try{
            int port = Integer.parseInt(grpcPort);

            Server grpcServer = ServerBuilder.forPort(15001)
                    .addService(securityGroupGRPCController)
                    .build()
                    .start();
            log.info("GRPC Server started, listening on {}", port);
            return grpcServer;
        }catch (NumberFormatException e){
            throw new IllegalStateException("Invalid gRPC port.");
        }
    }
}

package com.intralogix.users.clients;


import com.intralogix.common.exceptions.ServiceClientException;
import com.intralogix.common.internal.UserIds;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;


@Service
public class WorkspaceClient {

    private final RestClient restClient;
    private final String API_KEY;

    public WorkspaceClient(Environment environment) {

        String workspaceUrl = environment.getProperty("service.workspace-url", String.class);
        String apiKey = environment.getProperty("service.api-key", String.class);
        if(workspaceUrl == null || apiKey == null) {
            throw new IllegalStateException("Workspace url not found");
        }
        this.API_KEY = apiKey;
        this.restClient = RestClient.builder()
                .baseUrl(workspaceUrl)
                .build();
    }


}

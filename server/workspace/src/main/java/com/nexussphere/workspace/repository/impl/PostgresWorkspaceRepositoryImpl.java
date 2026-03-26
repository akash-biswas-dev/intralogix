package com.nexussphere.workspace.repository.impl;

import com.nexussphere.workspace.exceptions.DatasourceOperationFailedException;
import com.nexussphere.workspace.models.Workspaces;
import com.nexussphere.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PostgresWorkspaceRepositoryImpl implements WorkspaceRepository {

    private final DatabaseClient databaseClient;



    @Override
    public Mono<Workspaces> save(Workspaces workspaces) {

        String id = UUID.randomUUID().toString();

        Mono<Workspaces> savedWorksapceMono = databaseClient
                .sql("INSERT INTO workspaces(id, workspace_name, description, created_on) " +
                        "VALUES (:id, :name, :description, :createdOn)")
                .bind("id", id)
                .bind("name", workspaces.getName())
                .bind("description", workspaces.getDescription())
                .bind("createdOn", workspaces.getCreatedOn())
                .fetch()
                .rowsUpdated()
                .flatMap(rows -> {
                    if (rows ==0) {
                        String msg = String.format("Failed to save workspace with name: %s.", workspaces.getName());
                        return Mono.error(new DatasourceOperationFailedException(msg));
                    }
                    workspaces.setId(id); // set generated id
                    return Mono.just(workspaces);
                });
        return DataSourceUtility.handleMonoError(savedWorksapceMono);
    }
}

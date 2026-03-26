package com.nexussphere.workspace.repository;

import com.nexussphere.workspace.exceptions.DatasourceOperationFailedException;
import com.nexussphere.workspace.models.Workspaces;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PostgresWorkspaceRepositoryImpl implements WorkspaceRepository {

    private final DatabaseClient databaseClient;


    private static <T> Mono<T> handleMonoError(Mono<T> publisher){
        return publisher
                .onErrorResume(Throwable.class, (err)->{
                    return Mono.error(new DatasourceOperationFailedException(err.getMessage()));
                });
    }

    @Override
    public Mono<Workspaces> createWorkspace(Workspaces workspaces) {

        String id = UUID.randomUUID().toString();

        Mono<Workspaces> savedWorksapceMono = databaseClient
                .sql("INSERT INTO workspaces(id, workspace_name, description, created_on, owned_by) " +
                        "VALUES (:id, :name, :description, :created_on, :owned_by)")
                .bind("id", id)
                .bind("name", workspaces.getName())
                .bind("description", workspaces.getDescription())
                .bind("created_on", workspaces.getCreatedOn())
                .bind("owned_by", workspaces.getOwnerId())
                .fetch()
                .rowsUpdated()
                .flatMap(rows -> {
                    if (rows != 1) {
                        String msg = String.format("Failed to create workspace with name : %s, for user:%s", workspaces.getName(), workspaces.getOwnerId());
                        return Mono.error(new DatasourceOperationFailedException(msg));
                    }
                    workspaces.setId(id); // set generated id
                    return Mono.just(workspaces);
                });
        return handleMonoError(savedWorksapceMono);
    }
}

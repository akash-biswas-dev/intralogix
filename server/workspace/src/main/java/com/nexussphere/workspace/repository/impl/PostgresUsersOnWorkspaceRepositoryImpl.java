package com.nexussphere.workspace.repository.impl;

import com.nexussphere.workspace.exceptions.DatasourceOperationFailedException;
import com.nexussphere.workspace.models.UsersOnWorkspace;
import com.nexussphere.workspace.repository.UsersOnWorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PostgresUsersOnWorkspaceRepositoryImpl implements UsersOnWorkspaceRepository {

    private final DatabaseClient databaseClient;

    @Override
    public Mono<UsersOnWorkspace> save(UsersOnWorkspace usersOnWorkspace) {
        Mono<UsersOnWorkspace> savedEntity = databaseClient
                .sql("INSERT INTO users_on_workspaces(user_id, workspace_id, user_type, joined_on)" +
                        "VALUES (:userId, :workspaceId, :userType,:joinedOn)")
                .bind("userId",usersOnWorkspace.getUserId())
                .bind("workspaceId", usersOnWorkspace.getWorkspaceId())
                .bind("userType",usersOnWorkspace.getUserType().name())
                .bind("joinedOn", LocalDate.now())
                .fetch()
                .rowsUpdated()
                .flatMap((count)->{
                    if(count==0){
                        String msg = String.format("Failed to add user %s to the workspace %s",usersOnWorkspace.getUserId(),usersOnWorkspace.getWorkspaceId());
                        return Mono.error(new DatasourceOperationFailedException(msg));
                    }
                    return Mono.just(usersOnWorkspace);
                });
        return DataSourceUtility.handleMonoError(savedEntity);
    }
}

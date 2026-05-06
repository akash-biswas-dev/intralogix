package com.biswasakashdev.nexussphere.workspace.repository.impl;

import com.biswasakashdev.nexussphere.common.response.PageResponse;
import com.biswasakashdev.nexussphere.common.exceptions.DataSourceOperationFailedException;
import com.biswasakashdev.nexussphere.workspace.dtos.dao.UsersOnWorkspaceInfo;
import com.biswasakashdev.nexussphere.workspace.models.UserType;
import com.biswasakashdev.nexussphere.workspace.models.UsersOnWorkspace;
import com.biswasakashdev.nexussphere.workspace.models.Workspaces;
import com.biswasakashdev.nexussphere.workspace.repository.UsersOnWorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostgresUsersOnWorkspaceRepositoryImpl implements UsersOnWorkspaceRepository {

    private final DatabaseClient databaseClient;

    @Override
    public Mono<UsersOnWorkspace> save(UsersOnWorkspace usersOnWorkspace) {
        return databaseClient
                .sql("INSERT INTO users_on_workspaces(user_id, workspace_id, user_type, joined_on,last_active)" +
                        "VALUES (:user_id, :workspace_id, :user_type,:joined_on, :last_active)")
                .bind("user_id", usersOnWorkspace.getUserId())
                .bind("workspace_id", usersOnWorkspace.getWorkspaceId())
                .bind("user_type", usersOnWorkspace.getUserType().name())
                .bind("joined_on", LocalDate.now())
                .bind("last_active", LocalDateTime.now())
                .fetch()
                .rowsUpdated()
                .flatMap((count) -> {
                    if (count == 0) {
                        String msg = String.format("Failed to add user %s to the workspace %s", usersOnWorkspace.getUserId(), usersOnWorkspace.getWorkspaceId());
                        return Mono.error(new DataSourceOperationFailedException(msg));
                    }
                    return Mono.just(usersOnWorkspace);
                });
    }

    // If requiredCount is greater than count, then return the last page.
    protected int getRequiredPage(int requiredPage, int pageSize, long totalCount) {

        long requiredCount = (long) requiredPage * pageSize;

        if (totalCount == 0) {
            return 1;
        } else if (totalCount < requiredCount) {
            return (int) Math.ceil((double) totalCount / pageSize);
        } else {
            return requiredPage;
        }
    }

    @Override
    public Mono<PageResponse<Map<String, UsersOnWorkspaceInfo>>> findAllUsersInWorkspace(String workspaceId, Integer requiredPage, Integer pageSize, Sort.Direction direction) {

        Mono<Long> userCountMono = databaseClient
                .sql("SELECT COUNT(*) FROM users_on_workspaces WHERE workspace_id = :workspace_id")
                .bind("workspace_id", workspaceId)
                .map((row) -> Objects.requireNonNull(row.get(0, Long.class)))
                .one();


        return userCountMono.flatMap((count) -> {

            int page = getRequiredPage(requiredPage, pageSize, count);

            int offSet = (page - 1) * pageSize;

            Mono<Map<String, UsersOnWorkspaceInfo>> userIdListMono = databaseClient
                    .sql("SELECT " +
                            "uw.user_id AS user_id, " +
                            "uw.last_active AS last_active, " +
                            "uw.user_type AS user_type " +
                            "FROM users_on_workspaces AS uw WHERE uw.workspace_id = :workspace_id " +
                            "LIMIT :page_size OFFSET :off_set")
                    .bind("workspace_id", workspaceId)
                    .bind("page_size", pageSize)
                    .bind("off_set", offSet)
                    .map((row, metadata) -> new UsersOnWorkspaceInfo(
                            row.get("user_id", String.class),
                            UserType.valueOf(row.get("user_type", String.class)),
                            row.get("last_active", LocalDateTime.class)
                    ))
                    .all()
                    .collectMap(
                            UsersOnWorkspaceInfo::userId, // key mapper
                            member -> member         // value mapper
                    );

            return userIdListMono.map(userList -> {
                int pageCount = (int) Math.ceil((double) count / pageSize);
                return new PageResponse<>(
                        page,
                        pageSize,
                        pageCount,
                        count,
                        userList
                );
            });
        });
    }

    @Override
    public Mono<PageResponse<Workspaces>> findAllWorkspacesWithUserId(String userId, Integer requiredPage, Integer pageSize, Sort.Direction direction) {
        Mono<Long> workspaceCountMono = databaseClient
                .sql("SELECT COUNT(*) FROM users_on_workspaces WHERE user_id = :userId")
                .bind("userId", userId)
                .map((row) -> Objects.requireNonNull(row.get(0, Long.class)))
                .one();

        return workspaceCountMono.flatMap((count) -> {

            int page = getRequiredPage(requiredPage, pageSize, count);

            int offSet = (page - 1) * pageSize;

            Mono<List<Workspaces>> workspaceIdListMono = databaseClient
                    .sql("SELECT " +
                            "w.id AS workspace_id, " +
                            "w.workspace_name AS workspace_name, " +
                            "w.created_on AS created_on, " +
                            "w.description AS description, " +
                            "owner.user_id AS owner_id, " +
                            "uw.last_active AS last_active, " +
                            "(SELECT COUNT(*) FROM users_on_workspaces WHERE workspace_id = w.id) AS user_count, " +
                            "(SELECT COUNT(*) FROM groups_on_workspaces WHERE workspace_id = w.id) AS owner_count " +
                            "FROM workspaces w JOIN users_on_workspaces uw ON " +
                            "w.id = uw.workspace_id AND uw.user_id = :user_id LEFT JOIN users_on_workspaces owner " +
                            "ON owner.workspace_id = w.id AND owner.user_type = 'OWNER' LIMIT :page_size OFFSET :off_set ")
                    .bind("user_id", userId)
                    .bind("page_size", pageSize)
                    .bind("off_set", offSet)
                    .map((row) -> Workspaces.builder()
                            .id(row.get("workspace_id", String.class))
                            .name(row.get("workspace_name", String.class))
                            .description(row.get("description", String.class))
                            .ownedBy(row.get("owner_id", String.class))
                            .lastActive(row.get("last_active", LocalDateTime.class))
                            .userCount(row.get("user_count", Long.class))
                            .groupCount(row.get("owner_count", Long.class))
                            .createdOn(row.get("created_on", LocalDate.class))
                            .build())
                    .all()
                    .collectList();

            return workspaceIdListMono.map(workspaceList -> {
                int pageCount = (int) Math.ceil((double) count / pageSize);
                return new PageResponse<>(
                        page,
                        pageSize,
                        pageCount,
                        count,
                        workspaceList
                );
            });
        });
    }


}

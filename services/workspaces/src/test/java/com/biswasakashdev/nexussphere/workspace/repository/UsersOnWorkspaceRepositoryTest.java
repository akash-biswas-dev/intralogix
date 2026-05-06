package com.biswasakashdev.nexussphere.workspace.repository;

import com.biswasakashdev.nexussphere.workspace.models.UserType;
import com.biswasakashdev.nexussphere.workspace.models.UsersOnWorkspace;
import com.biswasakashdev.nexussphere.workspace.models.Workspaces;
import com.biswasakashdev.nexussphere.workspace.repository.impl.PostgresUsersOnWorkspaceRepositoryImpl;
import com.biswasakashdev.nexussphere.workspace.repository.impl.PostgresWorkspaceRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Sort;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@Import(value = {
        PostgresWorkspaceRepositoryImpl.class,
        PostgresUsersOnWorkspaceRepositoryImpl.class
})
class UsersOnWorkspaceRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private UsersOnWorkspaceRepository usersOnWorkspaceRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private DatabaseClient databaseClient;


    @BeforeEach
    void beforeEach() {

        try {
            ClassPathResource resource = new ClassPathResource("db/data/data.sql");
            String sql = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            Flux.fromArray(sql.split(";"))
                    .map(String::trim)
                    .filter(statement -> !statement.isEmpty())
                    .concatMap(statement ->
                            databaseClient.sql(statement)
                                    .fetch()
                                    .rowsUpdated()
                    ).blockLast();

        } catch (Exception e) {
            throw new RuntimeException("Failed to load SQL file", e);
        }

    }


    @AfterEach
    void afterEach() {
        databaseClient.sql("TRUNCATE TABLE " +
                        "users_on_workspaces, " +
                        "workspaces, " +
                        "groups, " +
                        "groups_on_workspaces, " +
                        "users_on_groups RESTART IDENTITY CASCADE")
                .fetch()
                .rowsUpdated()
                .block();
    }

    @Test
    void shouldSaveUsersOnWorkspace() {
        Workspaces workspaces = Workspaces.builder()
                .name("Workspace 1")
                .description("Description 1")
                .createdOn(LocalDate.now())
                .build();

        String userId = "a-long-user-id";

        workspaceRepository
                .save(workspaces)
                .flatMap((savedWorkspace) -> {
                    UsersOnWorkspace usersOnWorkspace =
                            UsersOnWorkspace
                                    .builder()
                                    .workspaceId(savedWorkspace.getId())
                                    .userId(userId)
                                    .userType(UserType.ADMIN)
                                    .joinedOn(LocalDate.now())
                                    .build();
                    return usersOnWorkspaceRepository.save(usersOnWorkspace);
                })
                .as(StepVerifier::create)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    void shouldReturnTheLastPageIfAskedForPageNumberGreaterThanTotalPages() {
        usersOnWorkspaceRepository
                .findAllWorkspacesWithUserId("u1", 100, 10, Sort.Direction.ASC)
                .as(StepVerifier::create)
                .consumeNextWith((page) -> {
                    assertEquals(1, page.page());
                    assertEquals(1, page.totalPages());
                })
                .expectComplete()
                .verify();

    }

    @Test
    void shouldFindAllTheWorkspacesUserMemberOfWithOtherDetails() {
        usersOnWorkspaceRepository.findAllWorkspacesWithUserId("u1", 1, 10, Sort.Direction.ASC)
                .as(StepVerifier::create)
                .consumeNextWith((page) -> {
                    assertEquals(3, page.totalItems());
                })
                .expectComplete()
                .verify();
    }


}
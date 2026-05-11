package com.biswasakashdev.nexussphere.workspace.repository;

import com.biswasakashdev.nexussphere.workspace.models.Workspaces;
import com.biswasakashdev.nexussphere.workspace.repository.impl.PostgresWorkspaceRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.test.StepVerifier;

import java.time.LocalDate;


@Import(value = {
        PostgresWorkspaceRepositoryImpl.class
})
public class WorkspaceRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private DatabaseClient databaseClient;

    @AfterEach
    void afterEach() {
        databaseClient.sql("TRUNCATE TABLE workspaces RESTART IDENTITY CASCADE")
                .fetch()
                .rowsUpdated()
                .block();
    }


    @Test
    void saveWorkspace() {
        Workspaces workspaces = Workspaces
                .builder()
                .name("Test Workspace")
                .description("This is a test workspace.")
                .createdOn(LocalDate.now())
                .build();
        workspaceRepository
                .save(workspaces)
                .flatMap((savedWorkspace) -> workspaceRepository.findById(savedWorkspace.getId()))
                .as(StepVerifier::create)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    void deleteWorkspace() {
        Workspaces workspace = Workspaces.builder()
                .name("Test Workspace")
                .description("This is a test workspace.")
                .createdOn(LocalDate.now())
                .build();

        StepVerifier.create(
                        workspaceRepository.save(workspace)
                                .flatMap(saved ->
                                        workspaceRepository.deleteById(saved.getId())
                                )
                )
                .expectNext(true)   // delete success
                .verifyComplete();
    }


}

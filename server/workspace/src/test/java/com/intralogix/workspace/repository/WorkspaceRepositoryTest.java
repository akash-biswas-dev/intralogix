package com.intralogix.workspace.repository;

import com.intralogix.workspace.models.WorkspaceId;
import com.intralogix.workspace.models.WorkspaceType;
import com.intralogix.workspace.models.Workspaces;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@ActiveProfiles(value = "test")
class WorkspaceRepositoryTest {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Workspaces testWorkspaces;

    @BeforeEach
    void setUp() {
        String userId = "user-who-create";
        List<String> members = List.of("user-1","user-2","user-3");

        Workspaces workspaces = new Workspaces(
                userId,
                "Test",
                "Long description",
                WorkspaceType.IT,
                LocalDate.now()
                );
        this.testWorkspaces = entityManager.persist(workspaces);
    }


    @Test
    void findWorkspaceByUserIdAndOwnerId(){
        Workspaces savedWorkspace = workspaceRepository.findById(new WorkspaceId(
                this.testWorkspaces.getOwnerId(),
                this.testWorkspaces.getWorkspaceName())
        ).orElseThrow();
        assertEquals(savedWorkspace.getWorkspaceName(), this.testWorkspaces.getWorkspaceName());
        assertEquals(savedWorkspace.getOwnerId(), this.testWorkspaces.getOwnerId());
        assertNotNull(savedWorkspace.getCreatedOn());
        assertEquals(savedWorkspace.getCreatedOn(), this.testWorkspaces.getCreatedOn());
        assertEquals(savedWorkspace.getDescription(), this.testWorkspaces.getDescription());
        assertEquals(savedWorkspace.getWorkspaceType(), this.testWorkspaces.getWorkspaceType());
    }
}
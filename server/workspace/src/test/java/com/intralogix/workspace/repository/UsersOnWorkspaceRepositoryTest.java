package com.intralogix.workspace.repository;

import com.intralogix.workspace.dtos.dao.UsersOnWorkspaceMembers;
import com.intralogix.workspace.models.UsersOnWorkspace;
import com.intralogix.workspace.models.WorkspaceType;
import com.intralogix.workspace.models.Workspaces;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



@DataJpaTest
@ActiveProfiles(value = "test")
class UsersOnWorkspaceRepositoryTest {

    @Autowired
    private UsersOnWorkspaceRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    private Workspaces testWorkspaces;

    @BeforeEach
    void setUp(){
        String userId = "user-who-create";

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
    void shouldHaveTheSameUsersWhoMemberOfWorkspaces(){
        List<String> users = List.of("User1", "User2", "User3", "User4", "User5", "User6", "User7");

        users.forEach((user)->{
            UsersOnWorkspace usersOnWorkspace = new UsersOnWorkspace(
                    user,
                    this.testWorkspaces.getOwnerId(),
                    this.testWorkspaces.getWorkspaceName(),
                    LocalDate.now()
                    );
            entityManager.persist(usersOnWorkspace);
        });

        Page<UsersOnWorkspaceMembers> workspaceMembers = repository.findById_OwnerIdAndId_WorkspaceName(
                this.testWorkspaces.getOwnerId(),
                this.testWorkspaces.getWorkspaceName(),
                PageRequest.of(
                        0,
                        10,
                        Sort.by(Sort.Direction.ASC, "joinedOn")
                )
        );

        assertEquals(users.size(), workspaceMembers.getTotalElements());

        List<UsersOnWorkspaceMembers> savedUsersOnWorkspaceMembers = workspaceMembers.getContent();
        savedUsersOnWorkspaceMembers.forEach(member -> {
            assertTrue(users.contains(member.getId_UserId()));
            assertNotNull(member.getJoinedOn());
        });
    }

}
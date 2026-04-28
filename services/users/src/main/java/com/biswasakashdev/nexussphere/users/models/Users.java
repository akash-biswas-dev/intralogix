package com.biswasakashdev.nexussphere.users.models;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document(collection= "users")
public class Users {
    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    @Indexed(unique = true)
    private String email;
    private String password;
    @Indexed(name = "joined_on")
    private Instant joinedOn;
    @Indexed(name = "is_profile_completed")
    private Boolean profileCompleted;
    @Indexed(name = "is_account_locked")
    private Boolean accountLocked;

    private UserProfile userProfile;

}

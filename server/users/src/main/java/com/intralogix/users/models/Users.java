package com.intralogix.users.models;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Users {
    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    @Indexed(unique = true)
    private String email;
    private String password;
    @Indexed(name = "joined_on")
    private LocalDate joinedOn;
    @Indexed(name = "is_account_enabled")
    private Boolean isAccountEnabled;
    @Indexed(name = "is_account_locked")
    private Boolean isAccountLocked;

    private UserProfile userProfile;

}

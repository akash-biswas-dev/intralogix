package com.intralogix.users.models;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Users implements UserDetails {
    @Id
    private String id;
    @Indexed(unique = true)
    @Getter(AccessLevel.NONE)
    private String username;
    private String email;
    @Getter(AccessLevel.NONE)
    private String password;
    @Indexed(name = "joined_on")
    private LocalDate joinedOn;
    @Indexed(name = "date_on_birth")
    private LocalDate dateOfBirth;
    @Indexed(name = "is_account_enabled")
    private Boolean isAccountEnabled;
    @Indexed(name ="is_account_non_expired")
    private Boolean isAccountNonExpired;
    @Indexed(name = "is_account_non_locked")
    private Boolean isAccountNonLocked;

    private Role role;

    private UserProfile userProfile;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(String.format("ROLE_%s",this.role.name())));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.id;
    }


    public String getRealUsername(){
        return this.username;
    }


    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }


    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }


    @Override
    public boolean isEnabled() {
        return this.isAccountEnabled;
    }
}

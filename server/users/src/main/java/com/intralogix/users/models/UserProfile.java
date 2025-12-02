package com.intralogix.users.models;


import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDate;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserProfile {

    @Indexed(name = "first_name")
    private String firstName;
    @Indexed(name = "last_name")
    private String lastName;
    private Gender gender;
    @Indexed(name = "date_of_birth")
    private LocalDate dateOfBirth;
}

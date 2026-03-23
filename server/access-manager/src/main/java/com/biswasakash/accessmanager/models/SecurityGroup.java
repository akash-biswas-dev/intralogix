package com.biswasakash.accessmanager.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "security_group")
public class SecurityGroup {

    @Id
    private String id;

    private String name;
    private String description;


}

package com.biswasakash.accessmanager.repository;


import com.biswasakash.accessmanager.models.SecurityGroup;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SecurityGroupRepository extends ReactiveCrudRepository<SecurityGroup,String> {
}

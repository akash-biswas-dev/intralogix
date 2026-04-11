package com.biswasakashdev.nexussphere.accessmanager.repository;


import com.biswasakashdev.nexussphere.accessmanager.models.SecurityGroup;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SecurityGroupRepository extends ReactiveCrudRepository<SecurityGroup,String> {


}

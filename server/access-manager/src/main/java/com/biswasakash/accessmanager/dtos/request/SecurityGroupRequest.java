package com.biswasakash.accessmanager.dtos.request;

import java.util.List;

public record SecurityGroupRequest (
       String name,
       String description,
       List<String> userId
){
}

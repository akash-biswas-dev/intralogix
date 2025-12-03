package com.intralogix.common.dtos;

public record AuthToken(
        String token,
        Integer age
){
}

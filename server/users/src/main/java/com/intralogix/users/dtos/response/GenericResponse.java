package com.intralogix.users.dtos.response;


public record GenericResponse<T>(
        ResponseCode code,
        String message,
        T data
) {
}

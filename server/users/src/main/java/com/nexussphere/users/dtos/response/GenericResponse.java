package com.nexussphere.users.dtos.response;


public record GenericResponse<T>(
        ResponseCode code,
        String message,
        T data
) {
}

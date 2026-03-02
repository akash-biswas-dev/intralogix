package com.nexussphere.common.response;

public record ClientResponse<T>(
        boolean success,
        T data,
        String error
){
}

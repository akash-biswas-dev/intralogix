package com.intralogix.common.response;

public record ClientResponse<T>(
        boolean success,
        T data,
        String error
){
}

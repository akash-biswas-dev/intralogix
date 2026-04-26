package com.biswasakashdev.nexussphere.common.response;


import lombok.Builder;

@Builder
public record ClientResponse<T>(
        boolean success,
        T data,
        String error
){
}

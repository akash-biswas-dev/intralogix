package com.biswasakashdev.nexussphere.gateway.exceptions;


import lombok.Getter;

@Getter
public class ResourceNotAllowedException extends RuntimeException{


    private final String userId;

    public ResourceNotAllowedException(String userId,String message) {
        super(message);
        this.userId = userId;
    }

}

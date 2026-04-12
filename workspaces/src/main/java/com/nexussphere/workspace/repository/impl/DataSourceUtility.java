package com.nexussphere.workspace.repository.impl;

import com.nexussphere.workspace.exceptions.DatasourceOperationFailedException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DataSourceUtility {

    public static <T> Mono<T> handleMonoError(Mono<T> publisher){
        return publisher
                .onErrorResume(Throwable.class, (err)->{
                    return Mono.error(new DatasourceOperationFailedException(err.getMessage(),err.getCause()));
                });
    }

    public static <T> Flux<T> handleFluxError(Flux<T> publisher){
        return publisher
                .onErrorResume(Throwable.class, (err)->{
                    return Mono.error(new DatasourceOperationFailedException(err.getMessage()));
                });
    }



}

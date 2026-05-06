package com.biswasakashdev.nexussphere.common.response;

import java.util.List;
import java.util.function.Function;

public record PageResponse<T>(
        int page,
        int pageSize,
        int totalPages,
        long totalItems,
        List<T> content
) {

    public static<T,K> PageResponse<K> pageResponse(PageResponse<T> pageResponse, Function<T,K> mapper){
        List<K> list= pageResponse.content.stream().map(mapper).toList();
        return new PageResponse<>(
                pageResponse.page(),
                pageResponse.pageSize(),
                pageResponse.totalPages(),
                pageResponse.totalItems(),
                list
        );
    }
}

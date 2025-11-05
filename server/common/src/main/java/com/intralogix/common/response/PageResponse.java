package com.intralogix.common.response;


import java.util.List;

public record PageResponse <T>(
    Integer page,
    Integer size,
    Integer total,
    Boolean isLast,
    Boolean isFirst,
    List<T> data
){
}

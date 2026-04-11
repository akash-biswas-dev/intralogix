package com.biswasakashdev.nexussphere.users.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/secured")
public class SecureController {

    @GetMapping
    public Mono<String> secured(){
       return Mono.just("Hello, its a secured endpoint :) ");
    }
}

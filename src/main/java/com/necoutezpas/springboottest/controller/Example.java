package com.necoutezpas.springboottest.controller;

import com.necoutezpas.springboottest.model.Test;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class Example {

    @PostMapping("/")
    public Mono<Test> getTest(@RequestBody final Test test) {
        return Mono.just(test);
    }
}

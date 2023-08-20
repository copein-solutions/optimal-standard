package com.optimal.standard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping("/user/ping")
    public String ping() {
        return "pong";
    }

}

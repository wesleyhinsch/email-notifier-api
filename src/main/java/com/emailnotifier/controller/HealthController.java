package com.emailnotifier.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/hello")
    public String hello() {
        return "Olá! API está funcionando! 🚀";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}

package com.subham.journalApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HealthCheck {
    @GetMapping("/*")
    public String home(){
        return "Welcome to home page of Journal Application";
    }

    @GetMapping("/health-check")
    public String healthCheck(){
        return "OK";
    }
}

package com.tickettimer.backendserver;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    public String hello() {
        return "hello";
    }
}

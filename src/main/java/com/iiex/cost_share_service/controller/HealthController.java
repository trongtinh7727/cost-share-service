package com.iiex.cost_share_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/health")
    public String checkHealth() {
        // Custom health check logic can be added here
        return "{\"status\":\"UP\"}";
    }
}

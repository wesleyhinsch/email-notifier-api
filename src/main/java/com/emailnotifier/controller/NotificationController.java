package com.emailnotifier.controller;

import com.emailnotifier.dto.NotificationResponse;
import com.emailnotifier.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    
    private final NotificationService service;
    
    @GetMapping
    public ResponseEntity<List<NotificationResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
    
    @GetMapping("/monitor/{monitorId}")
    public ResponseEntity<List<NotificationResponse>> findByMonitor(@PathVariable Long monitorId) {
        return ResponseEntity.ok(service.findByMonitorId(monitorId));
    }
}

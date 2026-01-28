package com.emailnotifier.controller;

import com.emailnotifier.dto.EmailMonitorRequest;
import com.emailnotifier.dto.EmailMonitorResponse;
import com.emailnotifier.service.EmailMonitorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/monitors")
@RequiredArgsConstructor
public class EmailMonitorController {
    
    private final EmailMonitorService service;
    
    @PostMapping
    public ResponseEntity<EmailMonitorResponse> create(@Valid @RequestBody EmailMonitorRequest request) {
        EmailMonitorResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<List<EmailMonitorResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EmailMonitorResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<EmailMonitorResponse> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(service.toggleActive(id));
    }
}

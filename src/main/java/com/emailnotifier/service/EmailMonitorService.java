package com.emailnotifier.service;

import com.emailnotifier.dto.EmailMonitorRequest;
import com.emailnotifier.dto.EmailMonitorResponse;
import com.emailnotifier.entity.EmailMonitor;
import com.emailnotifier.repository.EmailMonitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailMonitorService {
    
    private final EmailMonitorRepository repository;
    
    public EmailMonitorResponse create(EmailMonitorRequest request) {
        EmailMonitor monitor = new EmailMonitor();
        monitor.setName(request.getName());
        monitor.setMonitoredEmail(request.getMonitoredEmail());
        monitor.setSenderFilter(request.getSenderFilter());
        monitor.setWhatsappNumber(request.getWhatsappNumber());
        
        EmailMonitor saved = repository.save(monitor);
        return EmailMonitorResponse.fromEntity(saved);
    }
    
    public List<EmailMonitorResponse> findAll() {
        return repository.findAll().stream()
                .map(EmailMonitorResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
    public EmailMonitorResponse findById(Long id) {
        EmailMonitor monitor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Monitor não encontrado"));
        return EmailMonitorResponse.fromEntity(monitor);
    }
    
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    public EmailMonitorResponse toggleActive(Long id) {
        EmailMonitor monitor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Monitor não encontrado"));
        monitor.setActive(!monitor.getActive());
        EmailMonitor saved = repository.save(monitor);
        return EmailMonitorResponse.fromEntity(saved);
    }
}

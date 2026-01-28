package com.emailnotifier.service;

import com.emailnotifier.dto.NotificationResponse;
import com.emailnotifier.entity.EmailMonitor;
import com.emailnotifier.entity.Notification;
import com.emailnotifier.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    
    private final NotificationRepository repository;
    
    public Notification createNotification(EmailMonitor monitor, String subject, 
                                          String body, String sender, LocalDateTime receivedAt) {
        Notification notification = new Notification();
        notification.setMonitor(monitor);
        notification.setEmailSubject(subject);
        notification.setEmailBody(body);
        notification.setSender(sender);
        notification.setReceivedAt(receivedAt);
        return repository.save(notification);
    }
    
    public List<NotificationResponse> findByMonitorId(Long monitorId) {
        return repository.findByMonitorIdOrderBySentAtDesc(monitorId).stream()
                .map(NotificationResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
    public List<NotificationResponse> findAll() {
        return repository.findAll().stream()
                .map(NotificationResponse::fromEntity)
                .collect(Collectors.toList());
    }
}

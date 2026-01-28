package com.emailnotifier.dto;

import com.emailnotifier.entity.Notification;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationResponse {
    private Long id;
    private Long monitorId;
    private String emailSubject;
    private String sender;
    private LocalDateTime receivedAt;
    private LocalDateTime sentAt;
    private Boolean success;
    private String errorMessage;
    
    public static NotificationResponse fromEntity(Notification entity) {
        NotificationResponse response = new NotificationResponse();
        response.setId(entity.getId());
        response.setMonitorId(entity.getMonitor().getId());
        response.setEmailSubject(entity.getEmailSubject());
        response.setSender(entity.getSender());
        response.setReceivedAt(entity.getReceivedAt());
        response.setSentAt(entity.getSentAt());
        response.setSuccess(entity.getSuccess());
        response.setErrorMessage(entity.getErrorMessage());
        return response;
    }
}

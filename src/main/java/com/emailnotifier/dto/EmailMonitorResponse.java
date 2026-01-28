package com.emailnotifier.dto;

import com.emailnotifier.entity.EmailMonitor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EmailMonitorResponse {
    private Long id;
    private String name;
    private String monitoredEmail;
    private String senderFilter;
    private String whatsappNumber;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime lastChecked;
    
    public static EmailMonitorResponse fromEntity(EmailMonitor entity) {
        EmailMonitorResponse response = new EmailMonitorResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setMonitoredEmail(entity.getMonitoredEmail());
        response.setSenderFilter(entity.getSenderFilter());
        response.setWhatsappNumber(entity.getWhatsappNumber());
        response.setActive(entity.getActive());
        response.setCreatedAt(entity.getCreatedAt());
        response.setLastChecked(entity.getLastChecked());
        return response;
    }
}

package com.emailnotifier.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "monitor_id", nullable = false)
    private EmailMonitor monitor;
    
    @Column(nullable = false)
    private String emailSubject;
    
    @Column(columnDefinition = "TEXT")
    private String emailBody;
    
    @Column(nullable = false)
    private String sender;
    
    @Column(nullable = false)
    private LocalDateTime receivedAt;
    
    @Column(nullable = false)
    private LocalDateTime sentAt = LocalDateTime.now();
    
    @Column(nullable = false)
    private Boolean success = true;
    
    private String errorMessage;
}

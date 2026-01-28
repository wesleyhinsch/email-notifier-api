package com.emailnotifier.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "email_monitors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailMonitor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String monitoredEmail;
    
    @Column(nullable = false)
    private String senderFilter;
    
    @Column(nullable = false)
    private String whatsappNumber;
    
    @Column(nullable = false)
    private Boolean active = true;
    
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private LocalDateTime lastChecked;
}

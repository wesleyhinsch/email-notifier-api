package com.emailnotifier.controller;

import com.emailnotifier.entity.EmailMonitor;
import com.emailnotifier.repository.EmailMonitorRepository;
import com.emailnotifier.service.NotificationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/webhook")
@RequiredArgsConstructor
@Slf4j
public class WebhookController {
    
    private final EmailMonitorRepository monitorRepository;
    private final NotificationService notificationService;
    
    @PostMapping("/email-received")
    public ResponseEntity<Map<String, Object>> emailReceived(@RequestBody EmailWebhookPayload payload) {
        log.info("Webhook recebido - De: {}, Para: {}, Assunto: {}", 
                payload.getFrom(), payload.getTo(), payload.getSubject());
        
        // Busca monitor ativo que corresponde ao email e remetente
        EmailMonitor monitor = monitorRepository.findByActiveTrue().stream()
                .filter(m -> m.getMonitoredEmail().equalsIgnoreCase(payload.getTo()) 
                        && m.getSenderFilter().equalsIgnoreCase(payload.getFrom()))
                .findFirst()
                .orElse(null);
        
        Map<String, Object> response = new HashMap<>();
        
        if (monitor == null) {
            response.put("status", "ignored");
            response.put("message", "Nenhum monitor ativo encontrado para este email/remetente");
            return ResponseEntity.ok(response);
        }
        
        // Salva notificação
        notificationService.createNotification(
                monitor, 
                payload.getSubject(), 
                payload.getBody(), 
                payload.getFrom(), 
                LocalDateTime.now()
        );
        
        // Atualiza última verificação
        monitor.setLastChecked(LocalDateTime.now());
        monitorRepository.save(monitor);
        
        // Retorna dados para n8n enviar WhatsApp
        response.put("status", "success");
        response.put("whatsappNumber", monitor.getWhatsappNumber());
        response.put("message", formatWhatsAppMessage(payload, monitor));
        
        return ResponseEntity.ok(response);
    }
    
    private String formatWhatsAppMessage(EmailWebhookPayload payload, EmailMonitor monitor) {
        return String.format(
                "📧 *Novo Email Recebido*\n\n" +
                "👤 *De:* %s\n" +
                "📨 *Para:* %s\n" +
                "📋 *Assunto:* %s\n\n" +
                "💬 *Mensagem:*\n%s",
                payload.getFrom(),
                monitor.getName(),
                payload.getSubject(),
                payload.getBody() != null && payload.getBody().length() > 200 
                        ? payload.getBody().substring(0, 200) + "..." 
                        : payload.getBody()
        );
    }
    
    @Data
    public static class EmailWebhookPayload {
        private String from;
        private String to;
        private String subject;
        private String body;
    }
}

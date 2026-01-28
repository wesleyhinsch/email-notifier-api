package com.emailnotifier.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailMonitorRequest {
    
    @NotBlank(message = "Nome é obrigatório")
    private String name;
    
    @NotBlank(message = "Email monitorado é obrigatório")
    @Email(message = "Email inválido")
    private String monitoredEmail;
    
    @NotBlank(message = "Remetente filtro é obrigatório")
    @Email(message = "Email do remetente inválido")
    private String senderFilter;
    
    @NotBlank(message = "Número WhatsApp é obrigatório")
    private String whatsappNumber;
}

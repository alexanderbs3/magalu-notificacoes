package com.tech.notificacoes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record NotificacaoRequestDTO(
        @Schema(description = "E-mail do destinatario da notificacao", example = "email@example.com")
        @NotBlank(message = "Destinatario é obrigatorio.")
        @Email(message = "Destinatario deve ser email valido")
        String destinatario,

        @Schema(description = "Mensagem a ser enviada na notificação",example = "Lembrete: Reunião ás 14h")
        @NotBlank(message = "Mensagem é obrigatoria.")
        String mensagem,


        @Schema(description = "Data e hora para envio da notificação", example = "2025-09-30T14:00:00")
        @Future(message = "Data de agendamento deve ser no futuro.")
        LocalDateTime dataAgendamento) {
}

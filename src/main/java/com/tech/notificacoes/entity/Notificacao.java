package com.tech.notificacoes.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificacoes")
@Data
public class Notificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Notificacao() {
    }

    public Notificacao(String destinatario, String mensagem, LocalDateTime dataAgendamento, StatusNotificacao status) {
        this.destinatario = destinatario;
        this.mensagem = mensagem;
        this.dataAgendamento = dataAgendamento;
        this.status = status;
    }

    @Column(nullable = false)
    private String destinatario;

    @Column(nullable = false)
    private String mensagem;

    @Column(nullable = false)
    private LocalDateTime dataAgendamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusNotificacao status;

    public enum StatusNotificacao {
        PENDENTE, ENVIADA, CANCELADA, FALHA
    }
}
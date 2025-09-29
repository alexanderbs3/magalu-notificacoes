package com.tech.notificacoes.service;

import com.tech.notificacoes.dto.NotificacaoRequestDTO;
import com.tech.notificacoes.entity.Notificacao;
import com.tech.notificacoes.repository.NotificacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;

    public NotificacaoService(NotificacaoRepository notificacaoRepository) {
        this.notificacaoRepository = notificacaoRepository;
    }

    // Agendar uma nova notificação
    public Notificacao agendar(NotificacaoRequestDTO dto) {
       Notificacao notificacao = new Notificacao(
       dto.destinatario(),
        dto.mensagem(),
        dto.dataAgendamento(),
        Notificacao.StatusNotificacao.PENDENTE
       );
       return notificacaoRepository.save(notificacao);
    }

    // Consultar por ID
    public Optional<Notificacao> consultarPorId(Long id) {
        return notificacaoRepository.findById(id);
    }

    // Consultar com paginação e filtro por status (opcional)
    public Page<Notificacao> consultarTodos(Pageable pageable, Notificacao.StatusNotificacao status) {
        if (status != null) {
            return notificacaoRepository.findByStatus(status, pageable);
        }
        return notificacaoRepository.findAll(pageable);
    }

    // Cancelar uma notificação
    public void cancelar(Long id) {
        Optional<Notificacao> optional = notificacaoRepository.findById(id);
        if (optional.isPresent()) {
            Notificacao notificacao = optional.get();
            if (notificacao.getStatus() == Notificacao.StatusNotificacao.PENDENTE) {
                notificacao.setStatus(Notificacao.StatusNotificacao.CANCELADA);
                notificacaoRepository.save(notificacao);
            } else {
                throw new IllegalStateException("Só é possível cancelar notificações pendentes.");
            }
        } else {
            throw new IllegalArgumentException("Notificação não encontrada.");
        }
    }

    //atualizar notificacao (usada pelo scheduler)
    public Notificacao atualizar(Notificacao notificacao) {
        return notificacaoRepository.save(notificacao);
    }
}
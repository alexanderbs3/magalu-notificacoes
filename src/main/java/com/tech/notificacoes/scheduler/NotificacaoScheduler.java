package com.tech.notificacoes.scheduler;

import com.tech.notificacoes.dto.NotificacaoRequestDTO;
import com.tech.notificacoes.entity.Notificacao;
import com.tech.notificacoes.repository.NotificacaoRepository;
import com.tech.notificacoes.service.NotificacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NotificacaoScheduler {

    private static final Logger logger = LoggerFactory.getLogger(NotificacaoScheduler.class);

    private final NotificacaoService notificacaoService;
    private final NotificacaoRepository notificacaoRepository;

    public NotificacaoScheduler(NotificacaoService notificacaoService, NotificacaoRepository notificacaoRepository) {
        this.notificacaoService = notificacaoService;
        this.notificacaoRepository = notificacaoRepository;
    }

    /// Agendar uma nova notificacão
    public Notificacao agendar(NotificacaoRequestDTO dto) {
        logger.info("Agendando notificação para destinatario: {}", dto.destinatario());
        Notificacao notificacao = new Notificacao(
                dto.destinatario(),
                dto.mensagem(),
                dto.dataAgendamento(),
                Notificacao.StatusNotificacao.PENDENTE
        );
        Notificacao salva = notificacaoRepository.save(notificacao);
        logger.info("Notificacao agendada com ID: {}", salva.getId());
        return salva;
    }

    /// Consultar por ID
    public Optional<Notificacao> consultarPorId(Long id){
        logger.info("Consultando notificação com ID: {}", id);
        return notificacaoRepository.findById(id);
    }

    /// Consultar com paginação e filtro por status (opcional)
    public Page<Notificacao> consultarTodos(Pageable pageable, Notificacao.StatusNotificacao status) {
        logger.info("Consultando notificações com status: {} e página: {}", status, pageable);
        if (status != null) {
            return notificacaoRepository.findByStatus(status, pageable);
        }
        return notificacaoRepository.findAll(pageable);
    }

    /// Cancelar notificacão
    public void cancelar(Long id){
        logger.info("Cancelando notificacao por ID: {}", id);
        Optional<Notificacao> optional = notificacaoRepository.findById(id);
        if (optional.isPresent()){
            Notificacao notificacao = optional.get();
            if (notificacao.getStatus() == Notificacao.StatusNotificacao.PENDENTE){
                notificacao.setStatus(Notificacao.StatusNotificacao.CANCELADA);
                notificacaoRepository.save(notificacao);
                logger.info("Notificacao com ID: {} cancelada com sucesso.",id);
            }else{
                logger.warn("Notificacao com ID: {} nao encontrada.",id);
                throw new IllegalArgumentException("Notificacao não encontrada.");
            }
        }
    }

    /// Atualizar uma notificação (usado pelo Scheduler)
    public Notificacao atualizar(Notificacao notificacao){
        logger.info("Atualizando notificação ID: {} para status: {}", notificacao.getId(), notificacao.getStatus());
        return notificacaoRepository.save(notificacao);
    }
}
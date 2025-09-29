package com.tech.notificacoes.repository;

import com.tech.notificacoes.entity.Notificacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao,Long> {
    Page<Notificacao> findByStatus(Notificacao.StatusNotificacao status, Pageable pageable);
}

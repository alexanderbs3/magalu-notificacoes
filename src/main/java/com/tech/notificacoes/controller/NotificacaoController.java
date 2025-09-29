package com.tech.notificacoes.controller;

import com.tech.notificacoes.dto.NotificacaoRequestDTO;
import com.tech.notificacoes.entity.Notificacao;
import com.tech.notificacoes.service.NotificacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/notificacoes")
@Tag(name = "Notificações", description = "API para gerenciamento de notificações")
public class NotificacaoController {

    private final NotificacaoService service;

    public NotificacaoController(NotificacaoService service) {
        this.service = service;
    }

    @Operation(summary = "Agendar uma nova notificação", description = "Cria uma notificação com status PENDENTE para envio futuro.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificação agendada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Notificacao.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos no corpo da requisição",
                    content = @Content(mediaType = "application/json"))
    })
    /// CRIAR NOTIFICACOES
    @PostMapping
    public ResponseEntity<Notificacao> agendar(@Valid @RequestBody NotificacaoRequestDTO dto) {
        return ResponseEntity.ok(service.agendar(dto));
    }

    @Operation(summary = "Consultar notificação por ID", description = "Retorna os detalhes de uma notificação específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificação encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Notificacao.class))),
            @ApiResponse(responseCode = "404", description = "Notificação não encontrada",
                    content = @Content(mediaType = "application/json"))
    })
    /// BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Notificacao> consultarPorId(@Parameter(description = "ID da notificação") @PathVariable Long id) {
        Optional<Notificacao> notificacao = service.consultarPorId(id);
        return notificacao.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar notificações", description = "Retorna uma lista paginada de notificações, com filtro opcional por status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de notificações retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    })
    /// LISTAR TODOS
    @GetMapping
    public ResponseEntity<Page<Notificacao>> consultarTodos(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)") Pageable pageable,
            @Parameter(description = "Filtro opcional por status (PENDENTE, ENVIADA, CANCELADA, FALHA)")
            @RequestParam(required = false) Notificacao.StatusNotificacao status) {
        return ResponseEntity.ok(service.consultarTodos(pageable, status));
    }

    @Operation(summary = "Cancelar uma notificação", description = "Cancela uma notificação com status PENDENTE.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Notificação cancelada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Notificação não pode ser cancelada (status inválido ou não encontrada)",
                    content = @Content(mediaType = "application/json"))
    })
    /// DELETAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@Parameter(description = "ID da notificação") @PathVariable Long id) {
        service.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}
package com.diegoalexandro.rinhabackend.transacao.controller;

import com.diegoalexandro.rinhabackend.cliente.domain.Cliente;
import com.diegoalexandro.rinhabackend.transacao.dto.TransacaoRequestDTO;
import com.diegoalexandro.rinhabackend.transacao.dto.TransacaoResponseDTO;
import com.diegoalexandro.rinhabackend.transacao.service.NovaTransacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@Slf4j
@Validated
@RequiredArgsConstructor
public class TransacaoController {

    private final NovaTransacaoService service;

    @PostMapping("/{cliente_id}/transacoes")
    public ResponseEntity<TransacaoResponseDTO> novaTransacao(
            @PathVariable("cliente_id") Long clienteId,
            @Valid @RequestBody TransacaoRequestDTO requisicao
    ) {
        log.info("Recebendo requisição para cliente {}. {}", clienteId, requisicao);
        Cliente cliente = service.processar(requisicao, clienteId);
        return ResponseEntity.ok(new TransacaoResponseDTO(cliente.getLimite(), cliente.getSaldo()));
    }
}

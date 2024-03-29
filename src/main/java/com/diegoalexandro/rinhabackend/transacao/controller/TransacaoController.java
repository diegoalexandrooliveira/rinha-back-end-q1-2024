package com.diegoalexandro.rinhabackend.transacao.controller;

import com.diegoalexandro.rinhabackend.transacao.domain.Cliente;
import com.diegoalexandro.rinhabackend.transacao.dto.ExtratoResponseDTO;
import com.diegoalexandro.rinhabackend.transacao.dto.NovaTransacaoRequestDTO;
import com.diegoalexandro.rinhabackend.transacao.dto.NovaTransacaoResponseDTO;
import com.diegoalexandro.rinhabackend.transacao.service.ExtratoService;
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
    private final ExtratoService extratoService;

    @PostMapping("/{cliente_id}/transacoes")
    public ResponseEntity<NovaTransacaoResponseDTO> novaTransacao(
            @PathVariable("cliente_id") Long clienteId,
            @Valid @RequestBody NovaTransacaoRequestDTO requisicao
    ) {
        Cliente cliente = service.processar(requisicao, clienteId);
        return ResponseEntity.ok(new NovaTransacaoResponseDTO(cliente.getLimite(), cliente.getSaldo()));
    }

    @GetMapping("/{cliente_id}/extrato")
    public ResponseEntity<ExtratoResponseDTO> extrato(
            @PathVariable("cliente_id") Long clienteId
    ) {
        ExtratoResponseDTO extratoResponse = extratoService.getExtrato(clienteId);
        return ResponseEntity.ok(extratoResponse);
    }
}

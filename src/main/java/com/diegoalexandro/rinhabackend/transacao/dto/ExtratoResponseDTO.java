package com.diegoalexandro.rinhabackend.transacao.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ExtratoResponseDTO(
        SaldoResponseDTO saldo,
        List<TransacaoResponseDTO> ultimasTransacoes
) {
}

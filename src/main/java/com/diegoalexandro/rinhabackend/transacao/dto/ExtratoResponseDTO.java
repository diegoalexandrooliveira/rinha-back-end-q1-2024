package com.diegoalexandro.rinhabackend.transacao.dto;

import com.diegoalexandro.rinhabackend.transacao.repository.projection.TransacaoClienteProjection;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;
import java.util.Objects;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ExtratoResponseDTO(
        SaldoResponseDTO saldo,
        List<TransacaoResponseDTO> ultimasTransacoes
) {

    public static ExtratoResponseDTO from(List<TransacaoClienteProjection> projections) {
        TransacaoClienteProjection reference = projections.stream().findFirst().orElseThrow();
        final SaldoResponseDTO saldo = SaldoResponseDTO.from(reference);
        List<TransacaoResponseDTO> ultimasTransacoes = projections
                .stream()
                .filter(projection -> Objects.nonNull(projection.getValor()))
                .map(TransacaoResponseDTO::from)
                .toList();

        return new ExtratoResponseDTO(
                saldo,
                ultimasTransacoes
        );
    }
}

package com.diegoalexandro.rinhabackend.transacao.dto;

import com.diegoalexandro.rinhabackend.transacao.domain.Tipo;
import com.diegoalexandro.rinhabackend.transacao.repository.projection.TransacaoClienteProjection;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigInteger;
import java.time.ZonedDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TransacaoResponseDTO(
        BigInteger valor,
        Tipo tipo,
        String descricao,
        ZonedDateTime realizadaEm
) {

    public static TransacaoResponseDTO from(TransacaoClienteProjection projection) {
        return new TransacaoResponseDTO(
                projection.getValor(),
                projection.getTipo(),
                projection.getDescricao(),
                projection.getRealizadaEmConverted()
        );
    }
}

package com.diegoalexandro.rinhabackend.transacao.dto;

import com.diegoalexandro.rinhabackend.transacao.repository.projection.TransacaoClienteProjection;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigInteger;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SaldoResponseDTO(
        BigInteger total,
        ZonedDateTime dataExtracao,
        BigInteger limite
) {

    public static SaldoResponseDTO from(TransacaoClienteProjection reference) {
        return new SaldoResponseDTO(reference.getSaldo(), ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")), reference.getLimite());
    }
}

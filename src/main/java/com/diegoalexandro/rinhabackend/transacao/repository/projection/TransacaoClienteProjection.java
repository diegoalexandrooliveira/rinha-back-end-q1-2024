package com.diegoalexandro.rinhabackend.transacao.repository.projection;

import com.diegoalexandro.rinhabackend.transacao.domain.Tipo;

import java.math.BigInteger;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public interface TransacaoClienteProjection {

    BigInteger getSaldo();

    BigInteger getLimite();

    BigInteger getValor();

    String getDescricao();

    Tipo getTipo();

    Instant getRealizadaEm();

    default ZonedDateTime getRealizadaEmConverted() {
        return getRealizadaEm().atZone(ZoneId.of("UTC"));
    }
}

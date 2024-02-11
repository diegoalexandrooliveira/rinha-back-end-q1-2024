package com.diegoalexandro.rinhabackend.transacao.dto;

import java.math.BigInteger;

public record TransacaoResponseDTO(
        BigInteger limite,
        BigInteger saldo
) {
}

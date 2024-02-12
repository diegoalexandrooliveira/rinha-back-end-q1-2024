package com.diegoalexandro.rinhabackend.transacao.dto;

import java.math.BigInteger;

public record NovaTransacaoResponseDTO(
        BigInteger limite,
        BigInteger saldo
) {
}

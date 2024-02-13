package com.diegoalexandro.rinhabackend.transacao.dto;

import com.diegoalexandro.rinhabackend.transacao.domain.Tipo;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@ToString
public class NovaTransacaoRequestDTO {

    @NotNull
    @Min(1)
    @Digits(fraction = 0, integer = Integer.MAX_VALUE)
    private BigDecimal valor;

    @NotNull
    private Tipo tipo;

    @Size(max = 10)
    @NotEmpty
    private String descricao;

    public BigInteger getValor(){
        return BigInteger.valueOf(valor.intValue());
    }
}

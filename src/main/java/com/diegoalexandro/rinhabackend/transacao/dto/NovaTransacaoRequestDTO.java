package com.diegoalexandro.rinhabackend.transacao.dto;

import com.diegoalexandro.rinhabackend.transacao.domain.Tipo;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.ToString;

import java.math.BigInteger;

@Getter
@ToString
public class NovaTransacaoRequestDTO {

    @NotNull
    @Min(1)
    private BigInteger valor;

    @NotNull
    private Tipo tipo;

    @Size(max = 10)
    @NotEmpty
    private String descricao;
}

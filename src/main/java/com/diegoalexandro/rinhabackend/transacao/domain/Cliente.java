package com.diegoalexandro.rinhabackend.transacao.domain;

import com.diegoalexandro.rinhabackend.transacao.dto.NovaTransacaoRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.math.BigInteger;

@Entity
@Table(name = "cliente")
@Getter
public class Cliente {

    @Id
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "limite")
    private BigInteger limite;

    @Column(name = "saldo")
    private BigInteger saldo;

    public boolean podeTransacionar(NovaTransacaoRequestDTO novaTransacaoRequestDTO) {
        BigInteger novoSaldo = calculaSaldo(novaTransacaoRequestDTO.getTipo(), novaTransacaoRequestDTO.getValor());
        return novoSaldo.compareTo(limite.negate()) >= 0;
    }

    private BigInteger calculaSaldo(Tipo tipo, BigInteger valor) {
        return tipo == Tipo.CREDITO ? saldo.add(valor) : saldo.subtract(valor);
    }

    public void transaciona(NovaTransacaoRequestDTO novaTransacaoRequestDTO) {
        this.saldo = calculaSaldo(novaTransacaoRequestDTO.getTipo(), novaTransacaoRequestDTO.getValor());
    }
}

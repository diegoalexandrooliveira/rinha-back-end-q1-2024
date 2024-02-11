package com.diegoalexandro.rinhabackend.transacao.exceptions;

public class ClienteSemSaldoDisponivel extends RuntimeException {
    public ClienteSemSaldoDisponivel(Long clienteId) {
        super("Cliente %s não possui saldo disponivel para a transação.".formatted(clienteId));
    }
}

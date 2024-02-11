package com.diegoalexandro.rinhabackend.transacao.exceptions;

public class ClienteNaoExistenteException extends RuntimeException {
    public ClienteNaoExistenteException(Long clienteId) {
        super("Cliente %s não encontrado.".formatted(clienteId));
    }
}

package com.diegoalexandro.rinhabackend.transacao.service;

import com.diegoalexandro.rinhabackend.transacao.exceptions.ClienteNaoExistenteException;
import com.diegoalexandro.rinhabackend.transacao.repository.TransacaoRepository;
import com.diegoalexandro.rinhabackend.transacao.repository.projection.TransacaoClienteProjection;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExtratoService {

    private final TransacaoRepository repository;

    @Transactional
    public List<TransacaoClienteProjection> getExtrato(final Long clienteId) {
        List<TransacaoClienteProjection> results = repository.findByClienteId(clienteId);
        if (results.isEmpty()) {
            throw new ClienteNaoExistenteException(clienteId);
        }

        return results;
    }
}

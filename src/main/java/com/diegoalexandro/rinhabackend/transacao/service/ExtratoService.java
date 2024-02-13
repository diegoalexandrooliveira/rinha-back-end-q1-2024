package com.diegoalexandro.rinhabackend.transacao.service;

import com.diegoalexandro.rinhabackend.transacao.dto.ExtratoResponseDTO;
import com.diegoalexandro.rinhabackend.transacao.dto.SaldoResponseDTO;
import com.diegoalexandro.rinhabackend.transacao.dto.TransacaoResponseDTO;
import com.diegoalexandro.rinhabackend.transacao.exceptions.ClienteNaoExistenteException;
import com.diegoalexandro.rinhabackend.transacao.repository.TransacaoRepository;
import com.diegoalexandro.rinhabackend.transacao.repository.projection.TransacaoClienteProjection;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ExtratoService {

    private final TransacaoRepository repository;

    @Transactional
    public ExtratoResponseDTO getExtrato(final Long clienteId) {
        List<TransacaoClienteProjection> resultados = repository.findByClienteId(clienteId);
        if (resultados.isEmpty()) {
            throw new ClienteNaoExistenteException(clienteId);
        }

        TransacaoClienteProjection referenciaSaldo = resultados.stream().findFirst().orElseThrow();
        final SaldoResponseDTO saldo = SaldoResponseDTO.from(referenciaSaldo);

        List<TransacaoClienteProjection> resultadoFiltrado = resultados
                .stream()
                .filter(projecao -> projecao.getValor().compareTo(BigInteger.ZERO) > 0)
                .toList();
        if (resultadoFiltrado.size() == 11) {
            resultadoFiltrado = IntStream.range(0, 9).mapToObj(resultadoFiltrado::get).toList();
        }

        List<TransacaoResponseDTO> ultimasTransacoes = resultadoFiltrado
                .stream()
                .map(TransacaoResponseDTO::from)
                .toList();

        return new ExtratoResponseDTO(
                saldo,
                ultimasTransacoes
        );
    }
}

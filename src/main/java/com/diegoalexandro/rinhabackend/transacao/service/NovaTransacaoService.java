package com.diegoalexandro.rinhabackend.transacao.service;

import com.diegoalexandro.rinhabackend.cliente.domain.Cliente;
import com.diegoalexandro.rinhabackend.cliente.repository.ClienteRepository;
import com.diegoalexandro.rinhabackend.transacao.dto.TransacaoRequestDTO;
import com.diegoalexandro.rinhabackend.transacao.exceptions.ClienteNaoExistenteException;
import com.diegoalexandro.rinhabackend.transacao.exceptions.ClienteSemSaldoDisponivel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NovaTransacaoService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente processar(final TransacaoRequestDTO transacaoRequestDTO,
                          final Long clienteId) {
        final Optional<Cliente> optionalCliente = clienteRepository.findById(clienteId);
        if (optionalCliente.isEmpty()) {
            throw new ClienteNaoExistenteException(clienteId);
        }
        final Cliente cliente = optionalCliente.get();
        if (!cliente.podeTransacionar(transacaoRequestDTO)) {
            throw new ClienteSemSaldoDisponivel(clienteId);
        }

        cliente.transaciona(transacaoRequestDTO);

        clienteRepository.save(cliente);

        return cliente;
    }
}

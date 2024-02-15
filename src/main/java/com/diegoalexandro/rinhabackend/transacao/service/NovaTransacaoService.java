package com.diegoalexandro.rinhabackend.transacao.service;

import com.diegoalexandro.rinhabackend.transacao.domain.Cliente;
import com.diegoalexandro.rinhabackend.transacao.domain.Transacao;
import com.diegoalexandro.rinhabackend.transacao.dto.NovaTransacaoRequestDTO;
import com.diegoalexandro.rinhabackend.transacao.exceptions.ClienteNaoExistenteException;
import com.diegoalexandro.rinhabackend.transacao.exceptions.ClienteSemSaldoDisponivel;
import com.diegoalexandro.rinhabackend.transacao.repository.ClienteLockControlRepository;
import com.diegoalexandro.rinhabackend.transacao.repository.ClienteRepository;
import com.diegoalexandro.rinhabackend.transacao.repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NovaTransacaoService {

    private final ClienteRepository clienteRepository;
    private final TransacaoRepository transacaoRepository;
    private final ClienteLockControlRepository lockControlRepository;

    @Transactional
    public Cliente processar(final NovaTransacaoRequestDTO novaTransacaoRequestDTO,
                             final Long clienteId) {
        lockControlRepository.lockCliente(clienteId);
        final Optional<Cliente> optionalCliente = clienteRepository.findById(clienteId);
        if (optionalCliente.isEmpty()) {
            throw new ClienteNaoExistenteException(clienteId);
        }
        final Cliente cliente = optionalCliente.get();
        if (!cliente.podeTransacionar(novaTransacaoRequestDTO)) {
            throw new ClienteSemSaldoDisponivel(clienteId);
        }

        cliente.transaciona(novaTransacaoRequestDTO);

        clienteRepository.save(cliente);

        transacaoRepository.save(Transacao.from(novaTransacaoRequestDTO, cliente));

        return cliente;
    }
}

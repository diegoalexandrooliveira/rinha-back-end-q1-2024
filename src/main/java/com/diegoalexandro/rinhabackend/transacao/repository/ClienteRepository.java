package com.diegoalexandro.rinhabackend.transacao.repository;

import com.diegoalexandro.rinhabackend.transacao.domain.Cliente;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface ClienteRepository extends Repository<Cliente, Long> {

    Optional<Cliente> findById(Long clienteId);

    void save(Cliente cliente);
}

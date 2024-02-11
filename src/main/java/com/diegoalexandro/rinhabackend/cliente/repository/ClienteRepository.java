package com.diegoalexandro.rinhabackend.cliente.repository;

import com.diegoalexandro.rinhabackend.cliente.domain.Cliente;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface ClienteRepository extends Repository<Cliente, Long> {

    Optional<Cliente> findById(Long clienteId);

    void save(Cliente cliente);
}

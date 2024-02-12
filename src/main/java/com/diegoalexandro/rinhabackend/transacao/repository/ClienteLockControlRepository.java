package com.diegoalexandro.rinhabackend.transacao.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteLockControlRepository {

    private final EntityManager entityManager;

    public void lockCliente(Long clienteId) {
        entityManager.createNativeQuery("select 1 from pg_advisory_xact_lock(:id)")
                .setParameter("id", clienteId)
                .getSingleResult();
    }
}

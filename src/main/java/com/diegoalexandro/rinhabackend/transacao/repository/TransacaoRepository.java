package com.diegoalexandro.rinhabackend.transacao.repository;

import com.diegoalexandro.rinhabackend.transacao.domain.Transacao;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface TransacaoRepository extends Repository<Transacao, Long> {

    void save(Transacao transacao);
}

package com.diegoalexandro.rinhabackend.transacao.repository;

import com.diegoalexandro.rinhabackend.transacao.domain.Transacao;
import com.diegoalexandro.rinhabackend.transacao.repository.projection.TransacaoClienteProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface TransacaoRepository extends Repository<Transacao, Long> {

    void save(Transacao transacao);

    @Query(value = """
            select  c.saldo as saldo,
                    c.limite as limite,
                    t.valor as valor,
                    t.descricao as descricao,
                    t.tipo as tipo,
                    t.realizada_em as realizadaEm
            from transacao t
                     join cliente c on c.id = t.cliente_id
            where t.cliente_id = ?
            order by t.realizada_em desc
            limit 11
            """, nativeQuery = true)
    List<TransacaoClienteProjection> findByClienteId(Long clientId);
}

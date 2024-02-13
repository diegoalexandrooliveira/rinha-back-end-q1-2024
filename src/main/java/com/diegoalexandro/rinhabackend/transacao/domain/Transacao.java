package com.diegoalexandro.rinhabackend.transacao.domain;

import com.diegoalexandro.rinhabackend.transacao.dto.NovaTransacaoRequestDTO;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigInteger;
import java.time.ZonedDateTime;

@Entity
@Table(name = "transacao")
@Getter
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transacao_sequence")
    @SequenceGenerator(allocationSize = 100, initialValue = 10, sequenceName = "transacao_sequence", name = "transacao_sequence")
    private Long id;

    @JoinColumn(name = "cliente_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Column(name = "valor")
    private BigInteger valor;

    @CreationTimestamp
    @Column(name = "realizada_em")
    private ZonedDateTime realizadoEm;

    public static Transacao from(NovaTransacaoRequestDTO requestDTO, Cliente cliente) {
        Transacao transacao = new Transacao();
        transacao.cliente = cliente;
        transacao.tipo = requestDTO.getTipo();
        transacao.valor = requestDTO.getValor();
        transacao.descricao = requestDTO.getDescricao();

        return transacao;
    }

}

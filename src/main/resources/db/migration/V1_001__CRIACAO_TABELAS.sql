CREATE TABLE cliente (
                         id BIGINT PRIMARY KEY,
                         nome TEXT,
                         limite BIGINT,
                         saldo BIGINT
);

CREATE TABLE transacao (
                         id BIGINT PRIMARY KEY,
                         cliente_id BIGINT,
                         descricao character varying(10) NOT NULL,
                         tipo character varying(10) NOT NULL,
                         valor BIGINT,
                         realizada_em TIMESTAMP WITH TIME ZONE,
                         CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

CREATE SEQUENCE transacao_sequence INCREMENT BY 100 START WITH 10 NO CYCLE;
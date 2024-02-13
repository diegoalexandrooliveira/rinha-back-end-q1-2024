package com.diegoalexandro.rinhabackend.transacao.controller;

import com.diegoalexandro.rinhabackend.BaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class NovaTransacaoIT extends BaseIntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("delete from transacao");
        jdbcTemplate.execute("delete from cliente");
    }

    @DisplayName("Cliente não encontrado")
    @Test
    void notFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/clientes/1/transacoes")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                  "valor": 100000,
                                  "tipo": "d",
                                  "descricao": "pix"
                                }
                                """))
                .andExpect(
                        MockMvcResultMatchers.status().isNotFound()
                );
    }

    @DisplayName("Validação dos campos")
    @ParameterizedTest
    @ValueSource(strings = {
            "{\"valor\": null, \"tipo\": \"d\", \"descricao\": \"pix\"}",
            "{\"valor\": 10, \"tipo\": \"f\", \"descricao\": \"pix\"}",
            "{\"valor\": 10, \"tipo\": \"d\", \"descricao\": null}",
            "{\"valor\": 10, \"tipo\": \"d\", \"descricao\": \"descricao muito grande\"}",
            "{\"valor\": 0, \"tipo\": \"d\", \"descricao\": \"pix\"}",
            "{\"valor\": -1, \"tipo\": \"d\", \"descricao\": \"pix\"}",
            "{\"valor\": 1.2, \"tipo\": \"d\", \"descricao\": \"pix\"}"
    })
    void validation(String payload) throws Exception {
        jdbcTemplate.execute("INSERT INTO cliente (id, nome, limite, saldo) VALUES (1, 'João', 100000, 0);");
        mockMvc.perform(MockMvcRequestBuilders.post("/clientes/1/transacoes")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(payload))
                .andExpect(
                        MockMvcResultMatchers.status().isUnprocessableEntity()
                );
    }

    @DisplayName("Transação de debito com sucesso")
    @Test
    void success() throws Exception {
        jdbcTemplate.execute("INSERT INTO cliente (id, nome, limite, saldo) VALUES (1, 'João', 100000, 0);");
        mockMvc.perform(MockMvcRequestBuilders.post("/clientes/1/transacoes")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                  "valor": 100000,
                                  "tipo": "d",
                                  "descricao": "pix"
                                }
                                """))
                .andExpect(
                        MockMvcResultMatchers.status().is2xxSuccessful()
                );

        Map<String, Object> clienteResultRow = jdbcTemplate.queryForMap("select * from cliente");

        assertThat(clienteResultRow)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("saldo", -100000L);

        Map<String, Object> transacaoResultRow = jdbcTemplate.queryForMap("select * from transacao");

        assertThat(transacaoResultRow)
                .hasFieldOrPropertyWithValue("valor", 100000L)
                .hasFieldOrPropertyWithValue("descricao", "pix")
                .hasFieldOrPropertyWithValue("tipo", "DEBITO");
    }

    @DisplayName("Transação de credito com sucesso")
    @Test
    void credit() throws Exception {
        jdbcTemplate.execute("INSERT INTO cliente (id, nome, limite, saldo) VALUES (1, 'João', 100000, 0);");
        mockMvc.perform(MockMvcRequestBuilders.post("/clientes/1/transacoes")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                  "valor": 100000,
                                  "tipo": "c",
                                  "descricao": "pix"
                                }
                                """))
                .andExpect(
                        MockMvcResultMatchers.status().is2xxSuccessful()
                );

        Map<String, Object> clienteResultRow = jdbcTemplate.queryForMap("select * from cliente");

        assertThat(clienteResultRow)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("saldo", 100000L);

        Map<String, Object> transacaoResultRow = jdbcTemplate.queryForMap("select * from transacao");

        assertThat(transacaoResultRow)
                .hasFieldOrPropertyWithValue("valor", 100000L)
                .hasFieldOrPropertyWithValue("descricao", "pix")
                .hasFieldOrPropertyWithValue("tipo", "CREDITO");
    }

    @DisplayName("Transação de debito estourando o limite")
    @Test
    void limit() throws Exception {
        jdbcTemplate.execute("INSERT INTO cliente (id, nome, limite, saldo) VALUES (1, 'João', 100000, 0);");
        mockMvc.perform(MockMvcRequestBuilders.post("/clientes/1/transacoes")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                  "valor": 100000,
                                  "tipo": "d",
                                  "descricao": "pix"
                                }
                                """))
                .andExpect(
                        MockMvcResultMatchers.status().is2xxSuccessful()
                );

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes/1/transacoes")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                  "valor": 1,
                                  "tipo": "d",
                                  "descricao": "pix"
                                }
                                """))
                .andExpect(
                        MockMvcResultMatchers.status().isUnprocessableEntity()
                );

        Map<String, Object> clienteResultRow = jdbcTemplate.queryForMap("select * from cliente");

        assertThat(clienteResultRow)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("saldo", -100000L);

        Map<String, Object> transacaoResultRow = jdbcTemplate.queryForMap("select * from transacao");

        assertThat(transacaoResultRow)
                .hasFieldOrPropertyWithValue("valor", 100000L)
                .hasFieldOrPropertyWithValue("descricao", "pix")
                .hasFieldOrPropertyWithValue("tipo", "DEBITO");
    }

    @DisplayName("Transações em concorrência não podem estourar limite")
    @Test
    void concurrent() throws InterruptedException {
        jdbcTemplate.execute("INSERT INTO cliente (id, nome, limite, saldo) VALUES (1, 'João', 100000, 0);");
        Callable<MvcResult> endpoint = () -> {
            try {
                return mockMvc.perform(MockMvcRequestBuilders.post("/clientes/1/transacoes")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                          "valor": 20000,
                                          "tipo": "d",
                                          "descricao": "pix"
                                        }
                                        """))
                        .andReturn();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Future<MvcResult>> futures = executorService.invokeAll(IntStream.rangeClosed(1, 10).mapToObj(i -> endpoint).toList());

        futures.forEach(future -> {
            try {
                MvcResult unused = future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        Map<String, Object> clienteResultRow = jdbcTemplate.queryForMap("select * from cliente");

        assertThat(clienteResultRow)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("saldo", -100000L);

        List<Map<String, Object>> transacaoResults = jdbcTemplate.queryForList("select * from transacao");

        assertThat(transacaoResults)
                .hasSize(5);
    }

}
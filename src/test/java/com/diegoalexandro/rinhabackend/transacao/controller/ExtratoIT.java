package com.diegoalexandro.rinhabackend.transacao.controller;

import com.diegoalexandro.rinhabackend.BaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class ExtratoIT extends BaseIntegrationTest {

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
        mockMvc.perform(MockMvcRequestBuilders.get("/clientes/1/extrato"))
                .andExpect(
                        MockMvcResultMatchers.status().isNotFound()
                );
    }

//    @DisplayName("Extrato com sucesso")
//    @Test
//    void success() throws Exception {
//        jdbcTemplate.execute("INSERT INTO cliente (id, nome, limite, saldo) VALUES (1, 'João', 100000, 0);");
//        mockMvc.perform(MockMvcRequestBuilders.post("/clientes/1/transacoes")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content("""
//                                {
//                                  "valor": 100000,
//                                  "tipo": "d",
//                                  "descricao": "pix"
//                                }
//                                """))
//                .andExpect(
//                        MockMvcResultMatchers.status().is2xxSuccessful()
//                );
//
//
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/clientes/1/extrato"))
//                .andExpect(
//                        MockMvcResultMatchers.status().is2xxSuccessful()
//                ).andReturn();
//
//        String jsonResponse = mvcResult.getResponse().getContentAsString();
//
//        assertThat(jsonResponse).isEqualTo("""
//                {"saldo":{"total":-100000,"data_extracao":"2024-02-12T18:45:09.400576102Z","limite":100000},"ultimas_transacoes":[{"valor":100000,"tipo":"d","descricao":"pix","realizada_em":"2024-02-12T18:45:09.350762Z"}]}
//                """);
//    }
}
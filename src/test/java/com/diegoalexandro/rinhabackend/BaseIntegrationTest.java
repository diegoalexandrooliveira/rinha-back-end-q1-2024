package com.diegoalexandro.rinhabackend;

import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = PostgreSQLInitializer.EnvInitializer.class)
@AutoConfigureMockMvc
@Tag("integration")
public abstract class BaseIntegrationTest {
}

package com.diegoalexandro.rinhabackend;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSQLInitializer {
    public static final PostgreSQLContainer<?> POSTGRESQL = new PostgreSQLContainer<>("postgres:16")
            .withPassword("postgres")
            .withUsername("postgres");

    static {
        POSTGRESQL.start();
    }

    static class EnvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=%s".formatted(POSTGRESQL.getJdbcUrl()),
                    "spring.datasource.username=postgres",
                    "spring.datasource.password=postgres"
            ).applyTo(applicationContext);
        }
    }
}

# rinha-back-end-q1-2024

Serviço escrito em Java + Spring Boot 3 e compilação nativa com GraalVM

Dependências
- Spring Data JPA
- Spring Web

Compilar

```shell
mvn clean -Pnative spring-boot:build-image 
```

Rodar testes stress

```shell
docker-compose deploy up -d
~/rinha-de-backend-2024-q1/executar-teste-local.sh 
```

# Desafio Itaú - Validação de JWT

Esta aplicação é uma API REST simples desenvolvida com Spring Boot que valida tokens JWT conforme as regras do desafio.

## Regras de Validação

Um token JWT é considerado válido se:
1. For um JWT válido (assinatura válida);
2. Contiver exatamente 3 claims: `Name`, `Role` e `Seed`;
3. A claim `Name` não pode conter caracteres numéricos;
4. A claim `Role` deve conter exatamente um dos valores: `Admin`, `Member` ou `External`;
5. A claim `Seed` deve ser um número primo;
6. O tamanho máximo da claim `Name` é de 256 caracteres.

## Tecnologias Utilizadas

- Java 21
- Spring Boot 4.1.0
- JWT Library (io.jsonwebtoken:jjwt)
- Maven

## Como Executar

### Pré-requisitos
- Java 21 instalado
- Maven instalado

### Passos
1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/desafio-itau.git
   cd desafio-itau
   ```

2. Compile o projeto:
   ```bash
   mvn clean install
   ```

3. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```
   A aplicação será iniciada na porta 8080 por padrão.

4. Para validar um token JWT, faça uma requisição GET:
   ```bash
   curl -X GET "http://localhost:8080/validate?token=SEU_JWT_AQUI"
   ```
   A resposta será `true` se o token for válido, ou `false` caso contrário.

## Exemplo de Uso

### Token Válido (exemplo do desafio)
```bash
curl -X GET "http://localhost:8080/validate?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJST0xFIjoiQWRtaW4iLCJTZWVkIjoiNzg0MSIsIk5hbWUiOiJPbmhpbm8gQXJhdWpvIn0.XXXXX"
```
Saída esperada: `true`

### Token Inválido (exemplo do desafio)
```bash
curl -X GET "http://localhost:8080/validate?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.UkVMRF9FTVBIRVIiLCJTZWVkIjoiNzIzNDEiLCJOYW1lIjoiTTQuYmlhIE9saXZpYWEifQ.XXXXX"
```
Saída esperada: `false`

## Estrutura do Código

- `src/main/java/com/arthurrocha/desafio_itau/controller/JwtValidationController.java`: Controller REST que expõe o endpoint `/validate`.
- `src/main/java/com/arthurrocha/desafio_itau/service/JwtValidationService.java`: Serviço que contém a lógica de validação do JWT.
- `src/main/java/com/arthurrocha/desafio_itau/DesafioItauApplication.java`: Classe principal da aplicação Spring Boot.

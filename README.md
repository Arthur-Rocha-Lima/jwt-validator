# Desafio Itaú - Validação de JWT

Esta aplicação é uma API REST desenvolvida com Spring Boot que valida tokens JWT conforme as regras do desafio.

## Regras de Validação

Um token JWT é considerado válido se:
1. For um JWT válido (estrutura correta);
2. Conter exatamente 3 claims: `Name`, `Role` e `Seed`;
3. A claim `Name` não pode ter carácter de números;
4. A claim `Role` deve conter exatamente um dos valores (Admin, Member e External);
5. A claim Seed deve ser um número primo;
6. O tamanho máximo da claim Name é de 256 caracteres.

## Tecnologias Utilizadas

- Java 21
- Spring Boot 4.1.0
- JWT Library (io.jsonwebtoken:jjwt)
- Maven

## Como Executar

### Pré-requisitos
- Docker instalado

### Passos
1. Clone o repositório:
   ```bash
   git clone https://github.com/Arthur-Rocha-Lima/jwt-validator
   cd desafio-itau
   ```

2. Build o projeto:
   ```bash
   docker compose build
   ```

3. Execute o container:
   ```bash
   docker compose up -d
   ```
   A aplicação será iniciada na porta 8080 por padrão.

## Informações da API

A validação do JWT é feita através de um endpoint POST:

**Endpoint:** `POST /jwt/validate`
**Content-Type:** `application/json`
**Request Body:**
```json
{
  "token": "seu_token_jwt_aqui"
}
```

**Respostas:**

✅ **Token válido** — `200 OK`
```text
true
```

❌ **Token inválido** (não atende às regras de validação) — `422 Unprocessable Entity`
```json
{
  "timestamp": "2026-07-15T10:30:00.000-03:00",
  "status": 422,
  "error": "Unprocessable Entity",
  "message": "Token inválido: Name has numbers"
}
```

❌ **Requisição mal formatada** (token nulo, vazio ou ausente) — `400 Bad Request`
```json
{
  "timestamp": "2026-07-15T10:30:00.000-03:00",
  "status": 400,
  "error": "Bad Request",
  "message": "token: Token cannot be blank"
}
```

❌ **Erro interno inesperado** — `500 Internal Server Error`
```json
{
  "timestamp": "2026-07-15T10:30:00.000-03:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Ocorreu um erro inesperado"
}
```

## Coleções Insomnia

Dentro do arquivo `Insomnia_Collection.yaml` existem as coleções do insomnia para esse desafio, contendo as seguintes requests:
- Request enviando um token válido (via POST com corpo JSON), onde deve receber o retorno true;
- Request enviando um token inválido (via POST com corpo JSON), onde deve receber o retorno false;
- Request do actuator para validar se a aplicação está executando.

## Organização do código

Essa API foi desenvolvida utilizando os padrões Controller e Service, separando cada camada com sua devida responsabilidade.

A controller é responsável por receber o request, encaminhar a mensagem para a service e retornar se o token é válido ou não.

A service recebe o token e faz todas as validações necessárias no código, como esse token JWT enviado não é um token criado pelo nosso serviço, foi presumido que não deveria validar a assinatura do token, por isso a service remove essa assinatura e apenas coleta as Claims que serão validadas.

Para a validação de cada claim foi criada uma classe específica com a anotação `Component` que implementa a interface `ClaimValidator`, dessa forma podemos separar cada validação em uma classe específica, facilitando a manutenção do código e deixando o Spring carregar essas classes automaticamente apenas mencionando a Interface.

Outra decisão feita no código foi criar um Enum para armazenar todas as possíveis Roles que devem ser aceitas no código, dessa forma definimos as roles de forma indireta. Ou seja, caso seja criado uma nova role, apenas será necessário alterar o Enum, não necessitando alterar nenhuma condicional do código, por exemplo.

## Observabilidade (Logging)

A aplicação utiliza Log4j2 para captura de logs com diferentes níveis:
- **DEBUG**: informações de diagnóstico detalhadas (como hash do token) para desenvolvedores.
- **INFO**: eventos normais de operação (token válido com dados não sensíveis).
- **WARN**: situações de aviso, como token inválido (motivo da falha).
- **ERROR**: erros inesperados que incluem stack trace para investigação.

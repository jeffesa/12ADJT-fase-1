# Relatório Técnico — Tech Challenge Fase 1

**Sistema de Gerenciamento de Usuários**

**Curso:** Pós-Tech em Arquitetura e Desenvolvimento Java — FIAP

**Repositório:** [https://github.com/jeffesa/12ADJT-fase-1](https://github.com/jeffesa/12ADJT-fase-1)

**Aplicação em produção:** [https://one2adjt-fase-1.onrender.com](https://one2adjt-fase-1.onrender.com)

---

## Sumário

1. [Descrição da Arquitetura](#1-descrição-da-arquitetura)
2. [Modelagem das Entidades e Relacionamentos](#2-modelagem-das-entidades-e-relacionamentos)
3. [Descrição dos Endpoints](#3-descrição-dos-endpoints)
4. [Documentação Swagger](#4-documentação-swagger)
5. [Coleção Postman](#5-coleção-postman)
6. [Estrutura do Banco de Dados](#6-estrutura-do-banco-de-dados)
7. [Execução com Docker Compose](#7-execução-com-docker-compose)

---

## 1. Descrição da Arquitetura

### 1.1 Visão Geral

O sistema foi desenvolvido como uma API RESTful utilizando **Java 17** e **Spring Boot 3.2.3**, seguindo uma arquitetura em camadas (Layered Architecture). A aplicação gerencia usuários de dois tipos — **Cliente** (`CUSTOMER`) e **Dono de Restaurante** (`RESTAURANT_OWNER`) — com funcionalidades de CRUD completo, autenticação por login/senha e troca de senha em endpoint separado.

### 1.2 Diagrama de Camadas

```
┌─────────────────────────────────────────────────────┐
│                    CLIENT (HTTP)                     │
└─────────────────────┬───────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────┐
│              Controller (REST API)                   │
│    Recebe requisições HTTP, valida entrada,          │
│    delega para o Service e retorna ResponseEntity    │
└─────────────────────┬───────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────┐
│                Service (Negócio)                     │
│    Regras de negócio, validações de unicidade,       │
│    hash de senha com BCrypt, orquestração            │
└─────────────────────┬───────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────┐
│              Repository (Dados)                      │
│    Acesso ao banco via Spring Data JPA               │
│    Queries derivadas do nome do método               │
└─────────────────────┬───────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────┐
│              PostgreSQL (Banco Relacional)            │
└─────────────────────────────────────────────────────┘
```

### 1.3 Tecnologias Utilizadas

| Tecnologia | Versão | Finalidade |
|---|---|---|
| Java | 17 LTS | Linguagem principal |
| Spring Boot | 3.2.3 | Framework web |
| Spring Data JPA | 3.2.3 | Persistência de dados |
| Spring Security | 3.2.3 | BCrypt para hash de senhas |
| PostgreSQL | 15 | Banco de dados relacional (dev/prod) |
| H2 | Runtime | Banco em memória para testes |
| Springdoc OpenAPI | 2.3.0 | Documentação Swagger |
| Docker | - | Containerização |
| Docker Compose | 3.9 | Orquestração de containers |
| JUnit 5 + Mockito | - | Testes unitários e de integração |
| JaCoCo | 0.8.11 | Cobertura de testes |
| Maven | 3.9+ | Build e gerenciamento de dependências |

### 1.4 Justificativa das Escolhas Técnicas

- **Java 17 LTS**: versão estável com suporte a records (usados nos DTOs), text blocks e pattern matching.
- **Spring Boot 3.2**: framework maduro com ecossistema completo (Web, Data, Security, Actuator).
- **PostgreSQL**: banco relacional robusto, gratuito e amplamente utilizado em produção.
- **BCrypt**: algoritmo de hash adaptativo, padrão da indústria para armazenamento seguro de senhas.
- **ProblemDetail (RFC 7807)**: padrão HTTP nativo do Spring 6 para respostas de erro padronizadas.
- **Docker Compose**: permite subir a aplicação e o banco com um único comando.

### 1.5 Padrões de Projeto

- **DTO Pattern**: separação entre camada de apresentação e domínio usando Java Records imutáveis.
- **Repository Pattern**: abstração de acesso a dados via Spring Data JPA.
- **Service Layer**: centralização da lógica de negócio.
- **Global Exception Handler**: tratamento centralizado de erros com `@RestControllerAdvice`.
- **Strategy Pattern (Profiles)**: configurações diferentes por ambiente (`dev`, `prod`, `test`).

### 1.6 Estrutura do Projeto

```
src/
├── main/java/com/fiap/fase1/
│   ├── Fase1SpringExplorerApplication.java   # Classe principal
│   ├── config/
│   │   ├── OpenApiConfig.java                # Configuração Swagger
│   │   └── SecurityConfig.java               # Configuração Spring Security + BCrypt
│   ├── controller/
│   │   └── UserController.java               # Endpoints REST
│   ├── service/
│   │   └── UserService.java                  # Lógica de negócio
│   ├── repository/
│   │   └── UserRepository.java               # Acesso a dados
│   ├── model/
│   │   ├── User.java                         # Entidade JPA
│   │   └── UserType.java                     # Enum (CUSTOMER, RESTAURANT_OWNER)
│   ├── dto/
│   │   ├── UserRequestDTO.java               # Criação de usuário
│   │   ├── UserUpdateDTO.java                # Atualização (sem senha)
│   │   ├── UserResponseDTO.java              # Retorno de usuário
│   │   ├── ChangePasswordDTO.java            # Troca de senha
│   │   ├── LoginRequestDTO.java              # Requisição de login
│   │   └── LoginResponseDTO.java             # Resposta de login
│   ├── exception/
│   │   ├── GlobalExceptionHandler.java       # Handler centralizado (ProblemDetail)
│   │   ├── UserNotFoundException.java
│   │   ├── EmailAlreadyExistsException.java
│   │   ├── LoginAlreadyExistsException.java
│   │   └── InvalidCredentialsException.java
│   └── validation/
│       ├── ValidPassword.java                # Anotação customizada
│       ├── PasswordValidator.java            # Validador de senha
│       ├── SafeInput.java                    # Anotação anti-XSS
│       └── SafeInputValidator.java           # Validador anti-XSS
├── main/resources/
│   ├── application.properties                # Configuração base
│   ├── application-dev.properties            # Profile dev (PostgreSQL Docker)
│   └── application-prod.properties           # Profile prod (Render.com)
└── test/
    ├── java/com/fiap/fase1/
    │   ├── controller/UserControllerTest.java    # 24 testes
    │   ├── service/UserServiceTest.java          # 20 testes
    │   ├── repository/UserRepositoryTest.java    # 10 testes
    │   ├── model/UserModelTest.java              # 8 testes
    │   ├── integration/UserIntegrationTest.java  # 25 testes
    │   └── validation/
    │       ├── PasswordValidatorTest.java        # 8 testes
    │       └── SafeInputValidatorTest.java       # 9 testes
    └── resources/
        └── application-test.properties           # Profile test (H2)
```

### 1.7 Ambientes

| Ambiente | Banco | Profile | Execução |
|---|---|---|---|
| Desenvolvimento | PostgreSQL (Docker) | `dev` | `docker-compose up` |
| Testes | H2 em memória | `test` | `mvn test` |
| Produção | PostgreSQL (Render.com) | `prod` | Deploy automático |

---

## 2. Modelagem das Entidades e Relacionamentos

### 2.1 Entidade User

A aplicação possui uma única entidade `User` mapeada para a tabela `users`:

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(min = 2, max = 100)
    @Column(nullable = false)
    private String name;

    @Email @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank @Size(min = 3, max = 50)
    @Column(nullable = false, unique = true)
    private String login;

    @NotBlank @Size(min = 8)
    @Column(nullable = false)
    private String password;

    @NotBlank @Size(max = 255)
    @Column(nullable = false)
    private String address;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType type;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime lastModifiedDate;
}
```

### 2.2 Enum UserType

```java
public enum UserType {
    RESTAURANT_OWNER,  // Dono de restaurante
    CUSTOMER           // Cliente
}
```

### 2.3 Diagrama ER

```
┌──────────────────────────────────────┐
│              users                    │
├──────────────────────────────────────┤
│ id                 BIGSERIAL (PK)    │
│ name               VARCHAR(100) NN   │
│ email              VARCHAR(255) NN UQ│
│ login              VARCHAR(50)  NN UQ│
│ password           VARCHAR(255) NN   │
│ address            VARCHAR(255) NN   │
│ type               VARCHAR(20)  NN   │
│ last_modified_date TIMESTAMP    NN   │
└──────────────────────────────────────┘
```

**Observações:**
- `email` e `login` possuem constraint `UNIQUE` no banco.
- `password` é armazenado como hash BCrypt (nunca em texto puro).
- `lastModifiedDate` é atualizado automaticamente via `@PrePersist` e `@PreUpdate`.
- `type` é armazenado como `STRING` (não ordinal) para legibilidade.

### 2.4 DTOs

| DTO | Uso | Campos |
|---|---|---|
| `UserRequestDTO` | Criação | name, email, login, password, address, type |
| `UserUpdateDTO` | Atualização (sem senha) | name, email, login, address, type |
| `UserResponseDTO` | Retorno | id, name, email, login, address, type, lastModifiedDate |
| `ChangePasswordDTO` | Troca de senha | currentPassword, newPassword |
| `LoginRequestDTO` | Login | login, password |
| `LoginResponseDTO` | Resposta login | message, userId, login, email, lastModifiedDate |

---

## 3. Descrição dos Endpoints

### 3.1 Versionamento

Todos os endpoints utilizam versionamento por URL: `/api/v1/usuarios`.

### 3.2 Tabela de Endpoints

| Método | Endpoint | Descrição | Status Sucesso | Status Erro |
|---|---|---|---|---|
| POST | `/api/v1/usuarios` | Criar usuário | 201 | 400, 409 |
| GET | `/api/v1/usuarios` | Listar todos | 200 | - |
| GET | `/api/v1/usuarios?name={nome}` | Buscar por nome | 200 | - |
| GET | `/api/v1/usuarios/{id}` | Buscar por ID | 200 | 404 |
| PUT | `/api/v1/usuarios/{id}` | Atualizar usuário | 200 | 400, 404, 409 |
| DELETE | `/api/v1/usuarios/{id}` | Deletar usuário | 204 | 404 |
| POST | `/api/v1/usuarios/login` | Validar login | 200 | 400, 401 |
| PATCH | `/api/v1/usuarios/{id}/password` | Trocar senha | 200 | 400, 404 |

### 3.3 Exemplos de Uso

#### Criar Usuário (CUSTOMER)

**Request:**
```http
POST /api/v1/usuarios
Content-Type: application/json

{
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "login": "joaosilva",
  "password": "Senha123",
  "address": "Rua das Flores, 123 - São Paulo/SP",
  "type": "CUSTOMER"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "login": "joaosilva",
  "address": "Rua das Flores, 123 - São Paulo/SP",
  "type": "CUSTOMER",
  "lastModifiedDate": "2026-04-23T10:30:00"
}
```

**Header:** `Location: /api/v1/usuarios/1`

#### Criar Usuário (RESTAURANT_OWNER)

**Request:**
```http
POST /api/v1/usuarios
Content-Type: application/json

{
  "name": "Maria Oliveira",
  "email": "maria.oliveira@restaurante.com",
  "login": "mariaoliveira",
  "password": "Restaurante1",
  "address": "Av. Paulista, 1000 - São Paulo/SP",
  "type": "RESTAURANT_OWNER"
}
```

**Response (201 Created):**
```json
{
  "id": 2,
  "name": "Maria Oliveira",
  "email": "maria.oliveira@restaurante.com",
  "login": "mariaoliveira",
  "address": "Av. Paulista, 1000 - São Paulo/SP",
  "type": "RESTAURANT_OWNER",
  "lastModifiedDate": "2026-04-23T10:31:00"
}
```

#### Erro: Email Duplicado (409)

**Request:** POST `/api/v1/usuarios` com email já cadastrado

**Response (409 Conflict):**
```json
{
  "type": "https://api.fiap.com/errors/conflict",
  "title": "Email já cadastrado",
  "status": 409,
  "detail": "Email já cadastrado: joao.silva@email.com",
  "instance": "/api/v1/usuarios"
}
```

#### Erro: Dados Inválidos (400)

**Request:** POST `/api/v1/usuarios` com body `{}`

**Response (400 Bad Request):**
```json
{
  "type": "https://api.fiap.com/errors/validation",
  "title": "Dados inválidos",
  "status": 400,
  "detail": "Erro de validação",
  "instance": "/api/v1/usuarios",
  "campos": {
    "name": "O nome é obrigatório",
    "email": "O email é obrigatório",
    "login": "O login é obrigatório",
    "password": "A senha é obrigatória",
    "address": "O endereço é obrigatório",
    "type": "O tipo de usuário é obrigatório"
  }
}
```

#### Listar Todos os Usuários

**Request:**
```http
GET /api/v1/usuarios
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "name": "João Silva",
    "email": "joao.silva@email.com",
    "login": "joaosilva",
    "address": "Rua das Flores, 123 - São Paulo/SP",
    "type": "CUSTOMER",
    "lastModifiedDate": "2026-04-23T10:30:00"
  }
]
```

#### Buscar Usuários por Nome

**Request:**
```http
GET /api/v1/usuarios?name=João
```

A busca é parcial e case-insensitive. Retorna 200 com lista vazia se nenhum resultado for encontrado.

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "name": "João Silva",
    "email": "joao.silva@email.com",
    "login": "joaosilva",
    "address": "Rua das Flores, 123 - São Paulo/SP",
    "type": "CUSTOMER",
    "lastModifiedDate": "2026-04-23T10:30:00"
  }
]
```

#### Buscar Usuário por ID

**Request:**
```http
GET /api/v1/usuarios/1
```

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "login": "joaosilva",
  "address": "Rua das Flores, 123 - São Paulo/SP",
  "type": "CUSTOMER",
  "lastModifiedDate": "2026-04-23T10:30:00"
}
```

**Erro (404 Not Found):**
```json
{
  "type": "https://api.fiap.com/errors/not-found",
  "title": "Usuário não encontrado",
  "status": 404,
  "detail": "Usuário não encontrado com id: 99",
  "instance": "/api/v1/usuarios/99"
}
```

#### Atualizar Usuário

**Request:**
```http
PUT /api/v1/usuarios/1
Content-Type: application/json

{
  "name": "João Silva Atualizado",
  "email": "joao.novo@email.com",
  "login": "joaosilva",
  "address": "Rua Nova, 456 - São Paulo/SP",
  "type": "CUSTOMER"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "João Silva Atualizado",
  "email": "joao.novo@email.com",
  "login": "joaosilva",
  "address": "Rua Nova, 456 - São Paulo/SP",
  "type": "CUSTOMER",
  "lastModifiedDate": "2026-04-23T11:00:00"
}
```

> Nota: O endpoint de atualização **não** permite alterar a senha. Para isso, existe o endpoint separado `PATCH /{id}/password`.

#### Deletar Usuário

**Request:**
```http
DELETE /api/v1/usuarios/1
```

**Response:** `204 No Content` (sem body)

#### Login (Validação de Credenciais)

**Request:**
```http
POST /api/v1/usuarios/login
Content-Type: application/json

{
  "login": "joaosilva",
  "password": "Senha123"
}
```

**Response (200 OK):**
```json
{
  "message": "Login realizado com sucesso",
  "userId": 1,
  "login": "joaosilva",
  "email": "joao.silva@email.com",
  "lastModifiedDate": "2026-04-23T10:30:00"
}
```

**Erro (401 Unauthorized):**
```json
{
  "type": "https://api.fiap.com/errors/unauthorized",
  "title": "Credenciais inválidas",
  "status": 401,
  "detail": "Login ou senha inválidos",
  "instance": "/api/v1/usuarios/login"
}
```

#### Trocar Senha (Endpoint Separado)

**Request:**
```http
PATCH /api/v1/usuarios/1/password
Content-Type: application/json

{
  "currentPassword": "Senha123",
  "newPassword": "NovaSenha456"
}
```

**Response (200 OK):**
```json
{
  "mensagem": "Senha alterada com sucesso"
}
```

**Erro — Senha atual incorreta (400):**
```json
{
  "type": "https://api.fiap.com/errors/bad-request",
  "title": "Requisição inválida",
  "status": 400,
  "detail": "Senha atual incorreta",
  "instance": "/api/v1/usuarios/1/password"
}
```

### 3.4 Tratamento de Erros (ProblemDetail — RFC 7807)

Todas as respostas de erro seguem o padrão ProblemDetail com os campos:

| Campo | Descrição |
|---|---|
| `type` | URI que identifica o tipo de erro |
| `title` | Título legível do erro |
| `status` | Código HTTP |
| `detail` | Descrição detalhada do erro |
| `instance` | URI do recurso que gerou o erro |
| `campos` | (apenas em erros de validação) Mapa campo → mensagem |

### 3.5 Regras de Validação

| Campo | Regras |
|---|---|
| `name` | Obrigatório, 2-100 caracteres, sem caracteres perigosos (`<>\"'\``) |
| `email` | Obrigatório, formato email válido, único no sistema |
| `login` | Obrigatório, 3-50 caracteres, único no sistema, sem caracteres perigosos |
| `password` | Obrigatório, mínimo 8 caracteres, pelo menos 1 maiúscula, 1 minúscula e 1 número |
| `address` | Obrigatório, máximo 255 caracteres, sem caracteres perigosos |
| `type` | Obrigatório, deve ser `CUSTOMER` ou `RESTAURANT_OWNER` |

---

## 4. Documentação Swagger

### 4.1 Acesso

A documentação interativa está disponível em:

- **Local:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **Produção:** [https://one2adjt-fase-1.onrender.com/swagger-ui.html](https://one2adjt-fase-1.onrender.com/swagger-ui.html)

O JSON da especificação OpenAPI está em: `/api-docs`

### 4.2 Configuração

A documentação é gerada automaticamente pelo Springdoc OpenAPI 2.3.0, configurada em `OpenApiConfig.java`:

```java
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de Gerenciamento de Usuários")
                .version("1.0.0")
                .description("API RESTful para gerenciamento de usuários..."))
            .tags(List.of(
                new Tag().name("Usuários").description("CRUD de usuários"),
                new Tag().name("Autenticação").description("Login e troca de senha")
            ));
    }
}
```

### 4.3 Anotações nos Endpoints

Todos os endpoints possuem anotações Swagger:

- `@Operation(summary, description)` — descrição do endpoint
- `@ApiResponses` — códigos de resposta possíveis
- `@Parameter` — descrição dos parâmetros
- `@RequestBody` com `@ExampleObject` — exemplos de requisição
- `@Schema` nos DTOs — descrição e exemplos de cada campo

### 4.4 Organização

Os endpoints são organizados em dois grupos (tags):

1. **Usuários** — CRUD completo (POST, GET, PUT, DELETE)
2. **Autenticação** — Login e troca de senha (POST /login, PATCH /password)

---

## 5. Coleção Postman

### 5.1 Localização

O arquivo da coleção está em: `docs/api-collection/fiap-fase1-usuarios.json`

A documentação de como importar está em: `docs/api-collection/README.md`

### 5.2 Como Importar

1. Abra o Postman
2. Clique em **Import** > **Upload Files**
3. Selecione o arquivo `fiap-fase1-usuarios.json`
4. A coleção será importada com variáveis pré-configuradas

### 5.3 Variáveis

| Variável | Valor | Descrição |
|---|---|---|
| `localUrl` | `http://localhost:8080` | Ambiente local |
| `prodUrl` | `https://one2adjt-fase-1.onrender.com` | Ambiente de produção |
| `userId` | `1` | Preenchido automaticamente ao criar usuário |

### 5.4 Estrutura

A coleção possui 50 requests organizados em duas pastas principais (`local` e `prod`), cada uma com subpastas:

```
📁 local
├── 📁 Usuários (19 requests)
│   ├── Criar Usuário (CUSTOMER) ✅ 201
│   ├── Criar Usuário (RESTAURANT_OWNER) ✅ 201
│   ├── Criar Usuário - Campos vazios ❌ 400
│   ├── Criar Usuário - Email inválido ❌ 400
│   ├── Criar Usuário - Senha fraca ❌ 400
│   ├── Criar Usuário - Senha curta ❌ 400
│   ├── Criar Usuário - Email duplicado ❌ 409
│   ├── Criar Usuário - Login duplicado ❌ 409
│   ├── Listar Usuários ✅ 200
│   ├── Buscar Usuários por Nome ✅ 200
│   ├── Buscar por Nome - Case Insensitive ✅ 200
│   ├── Buscar por Nome - Inexistente ✅ 200 (lista vazia)
│   ├── Buscar Usuário por ID ✅ 200
│   ├── Buscar Usuário por ID - Inexistente ❌ 404
│   ├── Atualizar Usuário ✅ 200
│   ├── Atualizar Usuário - ID inexistente ❌ 404
│   ├── Atualizar Usuário - Dados inválidos ❌ 400
│   ├── Deletar Usuário ✅ 204
│   └── Deletar Usuário - ID inexistente ❌ 404
├── 📁 Autenticação (8 requests)
│   ├── Login ✅ 200
│   ├── Login - Usuário inexistente ❌ 401
│   ├── Login - Senha incorreta ❌ 401
│   ├── Login - Campos vazios ❌ 400
│   ├── Trocar Senha ✅ 200
│   ├── Trocar Senha - Senha atual incorreta ❌ 400
│   ├── Trocar Senha - Nova senha fraca ❌ 400
│   └── Trocar Senha - ID inexistente ❌ 404
└── 📁 Health Check (1 request)
    └── Health ✅ 200

📁 prod (mesma estrutura com prodUrl)
```

### 5.5 Cenários Cobertos

| Cenário exigido no PDF | Presente na coleção |
|---|---|
| Cadastro de usuário válido | ✅ Criar CUSTOMER e RESTAURANT_OWNER |
| Tentativa de cadastro inválido (e-mail duplicado, campos faltando) | ✅ 6 cenários de erro |
| Alteração de senha com sucesso e erro (endpoint exclusivo) | ✅ Sucesso + 3 cenários de erro |
| Atualização de dados com sucesso e erro (endpoint distinto) | ✅ Sucesso + 2 cenários de erro |
| Busca de usuários pelo nome | ✅ 3 cenários (parcial, case-insensitive, inexistente) |
| Validação de login (obrigatória) | ✅ Sucesso + 3 cenários de erro |

### 5.6 Scripts de Teste

Cada request possui scripts de teste Postman que validam automaticamente:
- Status code esperado
- Estrutura do response body
- Valores específicos dos campos
- Preenchimento automático de variáveis (no POST de criação)

---

## 6. Estrutura do Banco de Dados

### 6.1 Tabela `users`

| Coluna | Tipo | Constraints | Descrição |
|---|---|---|---|
| `id` | `BIGSERIAL` | PK, AUTO_INCREMENT | Identificador único |
| `name` | `VARCHAR(100)` | NOT NULL | Nome do usuário |
| `email` | `VARCHAR(255)` | NOT NULL, UNIQUE | Email (único) |
| `login` | `VARCHAR(50)` | NOT NULL, UNIQUE | Login (único) |
| `password` | `VARCHAR(255)` | NOT NULL | Hash BCrypt da senha |
| `address` | `VARCHAR(255)` | NOT NULL | Endereço |
| `type` | `VARCHAR(20)` | NOT NULL | Tipo: CUSTOMER ou RESTAURANT_OWNER |
| `last_modified_date` | `TIMESTAMP` | NOT NULL | Data da última alteração |

### 6.2 Índices

- **PK:** `id` (índice primário)
- **UNIQUE:** `email` (índice único)
- **UNIQUE:** `login` (índice único)

### 6.3 DDL Gerado

```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    login VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    type VARCHAR(20) NOT NULL,
    last_modified_date TIMESTAMP NOT NULL
);
```

### 6.4 Configuração por Ambiente

| Ambiente | Banco | DDL Strategy | Configuração |
|---|---|---|---|
| Dev | PostgreSQL 15 (Docker) | `update` | `application-dev.properties` |
| Test | H2 em memória | `create-drop` | `application-test.properties` |
| Prod | PostgreSQL (Render.com) | `update` | `application-prod.properties` |

---

## 7. Execução com Docker Compose

### 7.1 Pré-requisitos

- Docker 20.10+
- Docker Compose 2.0+

### 7.2 Arquivo docker-compose.yml

```yaml
version: "3.9"

services:
  postgres:
    image: postgres:15
    container_name: fiap-fase1-postgres
    environment:
      POSTGRES_DB: fiap_fase1
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      TZ: America/Sao_Paulo
    ports:
      - "5433:5432"
    volumes:
      - fiap_fase1_postgres_data:/var/lib/postgresql/data
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U postgres"]
      interval: 10s
      timeout: 5s
      retries: 5
    restart: unless-stopped

  app:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: fiap-fase1-app
    environment:
      SPRING_PROFILES_ACTIVE: dev
      SERVER_PORT: 8080
      TZ: America/Sao_Paulo
    ports:
      - "8080:8080"
    depends_on:
      postgres:
        condition: service_healthy
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/actuator/health"]
      interval: 15s
      timeout: 5s
      retries: 5
    restart: unless-stopped

volumes:
  fiap_fase1_postgres_data:
```

### 7.3 Variáveis de Ambiente

| Variável | Valor | Descrição |
|---|---|---|
| `POSTGRES_DB` | `fiap_fase1` | Nome do banco |
| `POSTGRES_USER` | `postgres` | Usuário do banco |
| `POSTGRES_PASSWORD` | `postgres` | Senha do banco |
| `SPRING_PROFILES_ACTIVE` | `dev` | Profile Spring Boot |
| `SERVER_PORT` | `8080` | Porta da aplicação |
| `TZ` | `America/Sao_Paulo` | Timezone |

### 7.4 Passo a Passo

#### 1. Clonar o repositório

```bash
git clone https://github.com/jeffesa/12ADJT-fase-1.git
cd 12ADJT-fase-1
```

#### 2. Subir a aplicação completa (app + banco)

```bash
docker-compose up
```

Isso irá:
1. Baixar a imagem do PostgreSQL 15
2. Criar o banco `fiap_fase1`
3. Fazer build da aplicação Java (multi-stage Dockerfile)
4. Aguardar o banco ficar saudável (healthcheck)
5. Iniciar a aplicação Spring Boot na porta 8080

#### 3. Verificar se está rodando

```bash
# Verificar containers
docker-compose ps

# Testar health check
curl http://localhost:8080/actuator/health
```

**Resposta esperada:**
```json
{
  "status": "UP",
  "components": {
    "db": { "status": "UP" },
    "diskSpace": { "status": "UP" }
  }
}
```

#### 4. Acessar a aplicação

| Recurso | URL |
|---|---|
| API | http://localhost:8080/api/v1/usuarios |
| Swagger UI | http://localhost:8080/swagger-ui.html |
| Health Check | http://localhost:8080/actuator/health |

#### 5. Subir apenas o banco (para desenvolvimento local)

```bash
docker-compose up -d postgres
```

Depois, rode a aplicação pelo IntelliJ ou Maven com o profile `dev`.

#### 6. Parar tudo

```bash
docker-compose down
```

Para remover também os volumes (dados do banco):

```bash
docker-compose down -v
```

### 7.5 Dockerfile (Multi-stage Build)

```dockerfile
# Build Stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime Stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
```

O build é feito em duas etapas:
1. **Build Stage**: compila o projeto com Maven (imagem ~800MB, descartada)
2. **Runtime Stage**: executa apenas o JAR com JRE mínimo (imagem ~200MB)

---

## Testes Automatizados (Desafio Extra)

O projeto inclui **104+ testes automatizados** com JUnit 5 + Mockito:

| Classe de Teste | Tipo | Quantidade |
|---|---|---|
| `UserControllerTest` | Unitário (MockMvc) | 24 |
| `UserServiceTest` | Unitário (Mockito) | 20 |
| `UserRepositoryTest` | Integração (DataJpaTest) | 10 |
| `UserModelTest` | Unitário | 8 |
| `UserIntegrationTest` | Integração (SpringBootTest) | 25 |
| `PasswordValidatorTest` | Unitário | 8 |
| `SafeInputValidatorTest` | Unitário | 9 |

Para executar:

```bash
mvn test
```

---

*Relatório gerado em maio de 2026.*

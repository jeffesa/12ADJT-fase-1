# Documentação da API de Gerenciamento de Usuários

Base URL Local: `http://localhost:8080`  
Base URL Produção: `https://one2adjt-fase-1.onrender.com`  
Documentação interativa (Swagger): `http://localhost:8080/swagger-ui.html`

> ⚠️ **Observação:** O primeiro acesso ao ambiente de produção (Render.com) pode demorar 50 segundos ou mais, pois a instância gratuita entra em modo de espera após inatividade. Após o primeiro request, a velocidade é normal.

---

## DTOs

### UserRequestDTO — Criação de usuário

| Campo | Tipo | Obrigatório | Regras |
|-------|------|-------------|--------|
| `name` | string | sim | 2–100 caracteres, sem HTML |
| `email` | string | sim | formato de e-mail válido, único |
| `login` | string | sim | 3–50 caracteres, único, sem HTML |
| `password` | string | sim | mín. 8 chars, 1 maiúscula, 1 minúscula, 1 número |
| `address` | string | sim | máx. 255 caracteres, sem HTML |
| `type` | enum | sim | `CUSTOMER` ou `RESTAURANT_OWNER` |

### UserUpdateDTO — Atualização de usuário

Igual ao `UserRequestDTO`, porém **sem o campo `password`**.

### ChangePasswordDTO — Troca de senha

| Campo | Tipo | Obrigatório | Regras |
|-------|------|-------------|--------|
| `currentPassword` | string | sim | senha atual do usuário |
| `newPassword` | string | sim | mín. 8 chars, 1 maiúscula, 1 minúscula, 1 número |

### LoginRequestDTO — Login

| Campo | Tipo | Obrigatório |
|-------|------|-------------|
| `login` | string | sim |
| `password` | string | sim |

### UserResponseDTO — Retorno de usuário

| Campo | Tipo |
|-------|------|
| `id` | number |
| `name` | string |
| `email` | string |
| `login` | string |
| `address` | string |
| `type` | enum (`CUSTOMER` \| `RESTAURANT_OWNER`) |
| `lastModifiedDate` | datetime (ISO 8601) |

---

## Endpoints

### POST /api/v1/usuarios — Criar usuário

**Headers**
```
Content-Type: application/json
```

**Request body**
```json
{
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "login": "joaosilva",
  "password": "Senha123",
  "address": "Rua das Flores, 123 - São Paulo/SP",
  "type": "CUSTOMER"
}
```

**Resposta de sucesso — 201 Created**
```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "login": "joaosilva",
  "address": "Rua das Flores, 123 - São Paulo/SP",
  "type": "CUSTOMER",
  "lastModifiedDate": "2026-04-19T10:30:00"
}
```

**Erros**

| Status | Motivo |
|--------|--------|
| 400 | Dados inválidos (campo obrigatório ausente, senha fraca, HTML no input) |
| 409 | E-mail ou login já cadastrado |

**Exemplo de erro 400**
```json
{
  "type": "https://api.fiap.com/errors/validation",
  "title": "Dados inválidos",
  "status": 400,
  "detail": "Erro de validação",
  "instance": "/api/v1/usuarios",
  "campos": {
    "password": "A senha deve ter no mínimo 8 caracteres, incluindo letra maiúscula, minúscula e número"
  }
}
```

**Exemplo de erro 409**
```json
{
  "type": "https://api.fiap.com/errors/conflict",
  "title": "Email já cadastrado",
  "status": 409,
  "detail": "Email já cadastrado: joao.silva@email.com",
  "instance": "/api/v1/usuarios"
}
```

---

### GET /api/v1/usuarios — Listar usuários

**Resposta de sucesso — 200 OK**
```json
[
  {
    "id": 1,
    "name": "João Silva",
    "email": "joao.silva@email.com",
    "login": "joaosilva",
    "address": "Rua das Flores, 123 - São Paulo/SP",
    "type": "CUSTOMER",
    "lastModifiedDate": "2026-04-19T10:30:00"
  }
]
```

---

### GET /api/v1/usuarios/{id} — Buscar usuário por ID

**Path parameter**

| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `id` | number | ID do usuário |

**Resposta de sucesso — 200 OK**
```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "login": "joaosilva",
  "address": "Rua das Flores, 123 - São Paulo/SP",
  "type": "CUSTOMER",
  "lastModifiedDate": "2026-04-19T10:30:00"
}
```

**Erros**

| Status | Motivo |
|--------|--------|
| 404 | Usuário não encontrado |

**Exemplo de erro 404**
```json
{
  "type": "https://api.fiap.com/errors/not-found",
  "title": "Usuário não encontrado",
  "status": 404,
  "detail": "Usuário não encontrado com id: 99",
  "instance": "/api/v1/usuarios/99"
}
```

---

### PUT /api/v1/usuarios/{id} — Atualizar usuário

**Headers**
```
Content-Type: application/json
```

**Path parameter**

| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `id` | number | ID do usuário |

**Request body**
```json
{
  "name": "João Silva Atualizado",
  "email": "joao.novo@email.com",
  "login": "joaosilva",
  "address": "Av. Paulista, 1000 - São Paulo/SP",
  "type": "RESTAURANT_OWNER"
}
```

**Resposta de sucesso — 200 OK**
```json
{
  "id": 1,
  "name": "João Silva Atualizado",
  "email": "joao.novo@email.com",
  "login": "joaosilva",
  "address": "Av. Paulista, 1000 - São Paulo/SP",
  "type": "RESTAURANT_OWNER",
  "lastModifiedDate": "2026-04-19T11:00:00"
}
```

**Erros**

| Status | Motivo |
|--------|--------|
| 400 | Dados inválidos |
| 404 | Usuário não encontrado |
| 409 | E-mail ou login já pertence a outro usuário |

---

### DELETE /api/v1/usuarios/{id} — Deletar usuário

**Path parameter**

| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `id` | number | ID do usuário |

**Resposta de sucesso — 204 No Content**

*(sem corpo)*

**Erros**

| Status | Motivo |
|--------|--------|
| 404 | Usuário não encontrado |

---

### PATCH /api/v1/usuarios/{id}/password — Trocar senha

**Headers**
```
Content-Type: application/json
```

**Path parameter**

| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `id` | number | ID do usuário |

**Request body**
```json
{
  "currentPassword": "SenhaAtual123",
  "newPassword": "NovaSenha456"
}
```

**Resposta de sucesso — 200 OK**
```json
{
  "mensagem": "Senha alterada com sucesso"
}
```

**Erros**

| Status | Motivo |
|--------|--------|
| 400 | Senha atual incorreta ou nova senha não atende aos requisitos |
| 404 | Usuário não encontrado |

**Exemplo de erro 400**
```json
{
  "type": "https://api.fiap.com/errors/bad-request",
  "title": "Requisição inválida",
  "status": 400,
  "detail": "Senha atual incorreta",
  "instance": "/api/v1/usuarios/1/password"
}
```

---

### POST /api/v1/usuarios/login — Login

**Headers**
```
Content-Type: application/json
```

**Request body**
```json
{
  "login": "joaosilva",
  "password": "Senha123"
}
```

**Resposta de sucesso — 200 OK**
```json
{
  "message": "Login realizado com sucesso",
  "userId": 1,
  "login": "joaosilva",
  "email": "joao.silva@email.com",
  "lastModifiedDate": "2026-04-19T10:30:00"
}
```

**Erros**

| Status | Motivo |
|--------|--------|
| 401 | Login ou senha inválidos |

**Exemplo de erro 401**
```json
{
  "type": "https://api.fiap.com/errors/unauthorized",
  "title": "Credenciais inválidas",
  "status": 401,
  "detail": "Login ou senha inválidos",
  "instance": "/api/v1/usuarios/login"
}
```

---

## Códigos de Status HTTP

| Código | Significado |
|--------|-------------|
| 200 | Operação realizada com sucesso |
| 201 | Recurso criado com sucesso |
| 204 | Operação realizada sem conteúdo de retorno (ex: DELETE) |
| 400 | Requisição inválida — dados ausentes, formato incorreto ou regras de validação não atendidas |
| 401 | Não autorizado — credenciais inválidas |
| 404 | Recurso não encontrado |
| 409 | Conflito — e-mail ou login já cadastrado |

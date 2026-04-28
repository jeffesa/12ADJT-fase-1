# 📬 API Collection - FIAP Fase 1

Collection completa de testes para a API de Gerenciamento de Usuários.

## Arquivo

- `fiap-fase1-usuarios.json` — Collection Postman v2.1 com 50 requests (sucesso + erro)

## Como importar no Postman

1. Abra o Postman
2. Clique em **Import** (canto superior esquerdo)
3. Arraste o arquivo `fiap-fase1-usuarios.json` ou clique em **Upload Files**
4. A collection será importada com todas as variáveis pré-configuradas

## Variáveis da Collection

| Variável   | Valor padrão                            | Descrição                                |
|------------|-----------------------------------------|------------------------------------------|
| `localUrl` | `http://localhost:8080`                 | URL do ambiente local                    |
| `prodUrl`  | `https://one2adjt-fase-1.onrender.com`  | URL do ambiente de produção              |
| `userId`   | `1`                                     | Preenchido automaticamente ao criar user |

## Estrutura da Collection

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

## Endpoints cobertos

| Método | Endpoint                      | Cenários |
|--------|-------------------------------|----------|
| GET    | /actuator/health              | 200      |
| POST   | /api/v1/usuarios              | 201, 400, 409 |
| GET    | /api/v1/usuarios              | 200      |
| GET    | /api/v1/usuarios?name={nome}  | 200 (com resultados, case-insensitive, lista vazia) |
| GET    | /api/v1/usuarios/{id}         | 200, 404 |
| PUT    | /api/v1/usuarios/{id}         | 200, 400, 404 |
| DELETE | /api/v1/usuarios/{id}         | 204, 404 |
| POST   | /api/v1/usuarios/login        | 200, 400, 401 |
| PATCH  | /api/v1/usuarios/{id}/password| 200, 400, 404 |

## Testes automatizados

Cada request possui scripts de teste Postman que validam:
- Status code esperado
- Estrutura e valores do response body
- Preenchimento automático de variáveis (no POST de criação)

Para rodar todos os testes de uma vez, use o **Collection Runner** do Postman.

## Ordem de execução recomendada

1. **Health Check** — verificar se a API está rodando
2. **Criar CUSTOMER** — cria o usuário e preenche `userId` automaticamente
3. **Testes de sucesso** — listar, buscar por nome, buscar por ID, atualizar
4. **Testes de autenticação** — login, trocar senha
5. **Testes de erro** — validações, conflitos, not found
6. **Deletar usuário** — limpeza

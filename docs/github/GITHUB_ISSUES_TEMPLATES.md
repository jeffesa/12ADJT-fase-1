# 📝 Templates de Issues para GitHub

Use estes templates para criar as 30 issues no seu repositório GitHub.

---

## ÉPICO 1: Configuração Inicial

### TASK-001: Criar estrutura inicial do projeto Spring Boot

**Labels:** `priority: high`, `épico: configuração`, `pontos: 2`  
**Milestone:** Sprint 1 - Fundação

**Descrição:**

```markdown
## 📋 Descrição
Configurar o projeto Spring Boot utilizando o Spring Initializr com as dependências necessárias para desenvolvimento da API RESTful.

## ✅ Critérios de Aceitação
- [ ] Projeto criado via Spring Initializr
- [ ] Dependências incluídas: Spring Web, Spring Data JPA, Spring Validation, Driver do banco de dados
- [ ] Estrutura de pacotes criada (controller, service, repository, model, dto)
- [ ] Projeto compila sem erros
- [ ] Application.properties configurado com propriedades básicas

## 🔧 Dependências Técnicas
- Java 17+
- Spring Boot 3.x
- Maven ou Gradle

## 📊 Estimativa
2 pontos
```

---

### TASK-002: Configurar banco de dados relacional

**Labels:** `priority: high`, `épico: configuração`, `pontos: 3`  
**Milestone:** Sprint 1 - Fundação

**Descrição:**

```markdown
## 📋 Descrição
Configurar o banco de dados relacional (PostgreSQL ou MySQL) com as configurações necessárias para desenvolvimento e produção.

## ✅ Critérios de Aceitação
- [ ] Banco de dados escolhido e justificado
- [ ] Configurações de conexão no application.properties
- [ ] Configuração de profiles (dev, prod)
- [ ] Script de inicialização do banco (se necessário)
- [ ] Conexão testada e funcionando

## 📊 Estimativa
3 pontos
```

---

## ÉPICO 2: Entidade e Dados

### TASK-003: Criar entidade Usuario (User)

**Labels:** `priority: high`, `épico: entidade-dados`, `pontos: 3`  
**Milestone:** Sprint 1 - Fundação

**Descrição:**

```markdown
## 📋 Descrição
Desenvolver a entidade Usuario com todos os campos obrigatórios e anotações JPA necessárias.

## ✅ Critérios de Aceitação
- [ ] Entidade Usuario criada com os campos: id, nome, email, login, senha, dataUltimaAlteracao
- [ ] Anotações JPA configuradas (@Entity, @Table, @Id, @GeneratedValue)
- [ ] Validações implementadas (@NotNull, @Email, @Size)
- [ ] Campo dataUltimaAlteracao com atualização automática (@PreUpdate, @PrePersist)
- [ ] Getters, setters, construtores implementados
- [ ] Equals e hashCode baseados no ID

## 📊 Estimativa
3 pontos
```

---

### TASK-004: Criar repository para Usuario

**Labels:** `priority: high`, `épico: entidade-dados`, `pontos: 2`  
**Milestone:** Sprint 1 - Fundação

**Descrição:**

```markdown
## 📋 Descrição
Implementar a interface repository utilizando Spring Data JPA para operações de banco de dados.

## ✅ Critérios de Aceitação
- [ ] Interface UsuarioRepository criada estendendo JpaRepository
- [ ] Método customizado para buscar usuário por login
- [ ] Método customizado para buscar usuário por email
- [ ] Método customizado para validar login (findByLoginAndSenha)
- [ ] Queries funcionando corretamente

## 📊 Estimativa
2 pontos
```

---

## ÉPICO 3: Camada de Serviço

### TASK-005: Criar DTOs (Data Transfer Objects)

**Labels:** `priority: high`, `épico: service`, `pontos: 2`  
**Milestone:** Sprint 1 - Fundação

**Descrição:**

```markdown
## 📋 Descrição
Criar os DTOs necessários para requisições e respostas da API, separando a camada de apresentação da camada de domínio.

## ✅ Critérios de Aceitação
- [ ] UsuarioRequestDTO criado (para cadastro/atualização)
- [ ] UsuarioResponseDTO criado (para retorno de dados)
- [ ] LoginRequestDTO criado (para validação de login)
- [ ] LoginResponseDTO criado (para resposta de login)
- [ ] Validações Bean Validation nos DTOs de request
- [ ] Senha não exposta nos DTOs de response

## 📊 Estimativa
2 pontos
```

---

### TASK-006: Implementar service de gerenciamento de usuários

**Labels:** `priority: high`, `épico: service`, `pontos: 5`  
**Milestone:** Sprint 1 - Fundação

**Descrição:**

```markdown
## 📋 Descrição
Desenvolver a camada de serviço com toda a lógica de negócio para gerenciamento de usuários.

## ✅ Critérios de Aceitação
- [ ] UsuarioService criado com anotação @Service
- [ ] Método criarUsuario implementado
- [ ] Método atualizarUsuario implementado
- [ ] Método buscarUsuarioPorId implementado
- [ ] Método listarTodosUsuarios implementado
- [ ] Método deletarUsuario implementado
- [ ] Validações de negócio implementadas (email único, login único)
- [ ] Tratamento de exceções customizadas
- [ ] Senha criptografada antes de salvar (BCrypt)

## 📊 Estimativa
5 pontos
```

---

### TASK-007: Implementar validação de login

**Labels:** `priority: high`, `épico: service`, `pontos: 3`  
**Milestone:** Sprint 1 - Fundação

**Descrição:**

```markdown
## 📋 Descrição
Criar a funcionalidade de validação de credenciais de login do usuário.

## ✅ Critérios de Aceitação
- [ ] Método validarLogin no UsuarioService
- [ ] Comparação de senha utilizando BCrypt
- [ ] Retorno de mensagem de sucesso quando credenciais válidas
- [ ] Retorno de mensagem de erro quando credenciais inválidas
- [ ] Não expor informações sensíveis em caso de erro
- [ ] Logs de tentativas de login

## 📊 Estimativa
3 pontos
```

---

## ÉPICO 4: API RESTful

### TASK-008: Criar endpoint de cadastro de usuário (POST)

**Labels:** `priority: high`, `épico: api`, `pontos: 3`  
**Milestone:** Sprint 2 - API e Segurança

**Descrição:**

```markdown
## 📋 Descrição
Implementar o endpoint REST para criação de novos usuários no sistema.

## ✅ Critérios de Aceitação
- [ ] Endpoint POST /api/usuarios criado
- [ ] Recebe UsuarioRequestDTO no body
- [ ] Retorna UsuarioResponseDTO com status 201 Created
- [ ] Validações de entrada funcionando
- [ ] Tratamento de erros (email/login duplicado)
- [ ] Location header com URI do recurso criado

## 📊 Estimativa
3 pontos
```

---

### TASK-009: Criar endpoint de atualização de usuário (PUT)

**Labels:** `priority: high`, `épico: api`, `pontos: 3`  
**Milestone:** Sprint 2 - API e Segurança

**Descrição:**

```markdown
## 📋 Descrição
Implementar o endpoint REST para atualização de dados de usuários existentes.

## ✅ Critérios de Aceitação
- [ ] Endpoint PUT /api/usuarios/{id} criado
- [ ] Recebe UsuarioRequestDTO no body
- [ ] Retorna UsuarioResponseDTO com status 200 OK
- [ ] Campo dataUltimaAlteracao atualizado automaticamente
- [ ] Validação se usuário existe
- [ ] Tratamento de erros apropriado

## 📊 Estimativa
3 pontos
```

---

### TASK-010: Criar endpoint de exclusão de usuário (DELETE)

**Labels:** `priority: high`, `épico: api`, `pontos: 2`  
**Milestone:** Sprint 2 - API e Segurança

**Descrição:**

```markdown
## 📋 Descrição
Implementar o endpoint REST para exclusão de usuários pelo ID.

## ✅ Critérios de Aceitação
- [ ] Endpoint DELETE /api/usuarios/{id} criado
- [ ] Retorna status 204 No Content quando sucesso
- [ ] Retorna status 404 Not Found quando usuário não existe
- [ ] Exclusão física do registro no banco
- [ ] Logs da operação de exclusão

## 📊 Estimativa
2 pontos
```

---

### TASK-011: Criar endpoint de busca de usuário por ID (GET)

**Labels:** `priority: medium`, `épico: api`, `pontos: 2`  
**Milestone:** Sprint 2 - API e Segurança

**Descrição:**

```markdown
## 📋 Descrição
Implementar o endpoint REST para buscar um usuário específico pelo ID.

## ✅ Critérios de Aceitação
- [ ] Endpoint GET /api/usuarios/{id} criado
- [ ] Retorna UsuarioResponseDTO com status 200 OK
- [ ] Retorna status 404 Not Found quando usuário não existe
- [ ] Senha não exposta na resposta

## 📊 Estimativa
2 pontos
```

---

### TASK-012: Criar endpoint de listagem de usuários (GET)

**Labels:** `priority: medium`, `épico: api`, `pontos: 2`  
**Milestone:** Sprint 2 - API e Segurança

**Descrição:**

```markdown
## 📋 Descrição
Implementar o endpoint REST para listar todos os usuários cadastrados.

## ✅ Critérios de Aceitação
- [ ] Endpoint GET /api/usuarios criado
- [ ] Retorna lista de UsuarioResponseDTO com status 200 OK
- [ ] Senhas não expostas na resposta
- [ ] Retorna lista vazia quando não há usuários

## 📊 Estimativa
2 pontos
```

---

### TASK-013: Criar endpoint de validação de login (POST)

**Labels:** `priority: high`, `épico: api`, `pontos: 3`  
**Milestone:** Sprint 2 - API e Segurança

**Descrição:**

```markdown
## 📋 Descrição
Implementar o endpoint REST para validação de credenciais de login.

## ✅ Critérios de Aceitação
- [ ] Endpoint POST /api/usuarios/login criado
- [ ] Recebe LoginRequestDTO no body
- [ ] Retorna LoginResponseDTO com mensagem de sucesso (status 200)
- [ ] Retorna status 401 Unauthorized com mensagem quando credenciais inválidas
- [ ] Não expõe informações sensíveis em caso de erro

## 📊 Estimativa
3 pontos
```

---

## ÉPICO 5: Tratamento de Erros

### TASK-014: Implementar exception handler global

**Labels:** `priority: high`, `épico: erros`, `pontos: 3`  
**Milestone:** Sprint 2 - API e Segurança

**Descrição:**

```markdown
## 📋 Descrição
Criar um controlador global de exceções para padronizar as respostas de erro da API.

## ✅ Critérios de Aceitação
- [ ] @RestControllerAdvice criado
- [ ] Tratamento de MethodArgumentNotValidException
- [ ] Tratamento de exceções customizadas (UsuarioNotFoundException, etc)
- [ ] Tratamento de DataIntegrityViolationException
- [ ] Resposta de erro padronizada com timestamp, status, mensagem e path
- [ ] Logs de erros apropriados

## 📊 Estimativa
3 pontos
```

---

### TASK-015: Criar exceções customizadas

**Labels:** `priority: medium`, `épico: erros`, `pontos: 2`  
**Milestone:** Sprint 2 - API e Segurança

**Descrição:**

```markdown
## 📋 Descrição
Desenvolver exceções customizadas para situações específicas do domínio da aplicação.

## ✅ Critérios de Aceitação
- [ ] UsuarioNotFoundException criada
- [ ] EmailJaCadastradoException criada
- [ ] LoginJaCadastradoException criada
- [ ] CredenciaisInvalidasException criada
- [ ] Exceções estendem RuntimeException
- [ ] Mensagens descritivas

## 📊 Estimativa
2 pontos
```

---

## ÉPICO 6: Segurança

### TASK-016: Implementar criptografia de senha

**Labels:** `priority: high`, `épico: segurança`, `pontos: 2`  
**Milestone:** Sprint 2 - API e Segurança

**Descrição:**

```markdown
## 📋 Descrição
Configurar e implementar criptografia de senhas utilizando BCrypt.

## ✅ Critérios de Aceitação
- [ ] Dependência Spring Security adicionada (ou BCrypt standalone)
- [ ] PasswordEncoder configurado como Bean
- [ ] Senhas criptografadas antes de salvar no banco
- [ ] Validação de senha utilizando BCrypt na autenticação
- [ ] Senhas nunca armazenadas em texto plano

## 📊 Estimativa
2 pontos
```

---

### TASK-017: Implementar validações de segurança

**Labels:** `priority: medium`, `épico: segurança`, `pontos: 2`  
**Milestone:** Sprint 2 - API e Segurança

**Descrição:**

```markdown
## 📋 Descrição
Adicionar validações de segurança para campos sensíveis e regras de negócio.

## ✅ Critérios de Aceitação
- [ ] Validação de força de senha (mínimo 8 caracteres)
- [ ] Validação de formato de email
- [ ] Validação de unicidade de login e email
- [ ] Sanitização de inputs
- [ ] Proteção contra SQL Injection (via JPA)

## 📊 Estimativa
2 pontos
```

---

## ÉPICO 7: Docker

### TASK-018: Criar Dockerfile para aplicação Spring Boot

**Labels:** `priority: high`, `épico: docker`, `pontos: 3`  
**Milestone:** Sprint 3 - Docker e Testes

**Descrição:**

```markdown
## 📋 Descrição
Desenvolver o Dockerfile para containerizar a aplicação Spring Boot.

## ✅ Critérios de Aceitação
- [ ] Dockerfile criado na raiz do projeto
- [ ] Utiliza imagem base Java apropriada
- [ ] Multi-stage build implementado (build + runtime)
- [ ] Porta da aplicação exposta
- [ ] JAR da aplicação copiado corretamente
- [ ] Imagem otimizada (tamanho reduzido)
- [ ] Aplicação inicia corretamente no container

## 📊 Estimativa
3 pontos
```

---

### TASK-019: Criar docker-compose.yml

**Labels:** `priority: high`, `épico: docker`, `pontos: 4`  
**Milestone:** Sprint 3 - Docker e Testes

**Descrição:**

```markdown
## 📋 Descrição
Configurar o Docker Compose para orquestrar a aplicação e o banco de dados.

## ✅ Critérios de Aceitação
- [ ] docker-compose.yml criado na raiz do projeto
- [ ] Serviço da aplicação Spring Boot configurado
- [ ] Serviço do banco de dados configurado
- [ ] Network configurada entre os serviços
- [ ] Volumes configurados para persistência de dados
- [ ] Variáveis de ambiente configuradas
- [ ] Health checks implementados
- [ ] Ordem de inicialização correta (depends_on)
- [ ] Aplicação e banco sobem com um único comando

## 📝 Nota
- Docker Compose é para execução local
- Para produção, será usado Render.com (TASK-031)

## 📊 Estimativa
4 pontos
```

---

### TASK-020: Criar arquivo .dockerignore

**Labels:** `priority: low`, `épico: docker`, `pontos: 1`  
**Milestone:** Sprint 3 - Docker e Testes

**Descrição:**

```markdown
## 📋 Descrição
Criar arquivo .dockerignore para otimizar o build da imagem Docker.

## ✅ Critérios de Aceitação
- [ ] .dockerignore criado na raiz do projeto
- [ ] Arquivos desnecessários excluídos (target/, .git/, .idea/, etc)
- [ ] Build da imagem mais rápido
- [ ] Tamanho da imagem reduzido

## 📊 Estimativa
1 ponto
```

---

## ÉPICO 8: Testes

### TASK-021: Criar collection do Postman/Insomnia

**Labels:** `priority: high`, `épico: testes`, `pontos: 3`  
**Milestone:** Sprint 3 - Docker e Testes

**Descrição:**

```markdown
## 📋 Descrição
Desenvolver uma collection completa de testes para todos os endpoints da API.

## ✅ Critérios de Aceitação
- [ ] Collection criada com todos os endpoints
- [ ] Testes para cenários de sucesso
- [ ] Testes para cenários de erro
- [ ] Variáveis de ambiente configuradas
- [ ] Exemplos de requisições e respostas
- [ ] Arquivo JSON da collection exportado
- [ ] Documentação de como importar e usar

## 📊 Estimativa
3 pontos
```

---

### TASK-022: Criar testes unitários (opcional)

**Labels:** `priority: low`, `épico: testes`, `pontos: 5`  
**Milestone:** Sprint 3 - Docker e Testes

**Descrição:**

```markdown
## 📋 Descrição
Implementar testes unitários para as camadas de service e repository.

## ✅ Critérios de Aceitação
- [ ] Testes para UsuarioService criados
- [ ] Testes para UsuarioRepository criados
- [ ] Cobertura mínima de 70%
- [ ] Utilização de JUnit 5 e Mockito
- [ ] Testes passando com sucesso

## 📊 Estimativa
5 pontos
```

---

## ÉPICO 9: Documentação

### TASK-023: Documentar arquitetura do projeto

**Labels:** `priority: high`, `épico: documentação`, `pontos: 3`  
**Milestone:** Sprint 4 - Documentação e Entrega

**Descrição:**

```markdown
## 📋 Descrição
Criar documentação detalhada da arquitetura do projeto, explicando as camadas e decisões técnicas.

## ✅ Critérios de Aceitação
- [ ] README.md criado com visão geral do projeto
- [ ] Diagrama de arquitetura (camadas)
- [ ] Descrição das tecnologias utilizadas
- [ ] Justificativa das escolhas técnicas
- [ ] Estrutura de pacotes explicada
- [ ] Padrões de projeto utilizados documentados
- [ ] Seção sobre deploy no Render.com
- [ ] Arquitetura de produção vs desenvolvimento explicada

## 📊 Estimativa
3 pontos
```

---

### TASK-024: Documentar endpoints da API

**Labels:** `priority: high`, `épico: documentação`, `pontos: 3`  
**Milestone:** Sprint 4 - Documentação e Entrega

**Descrição:**

```markdown
## 📋 Descrição
Criar documentação completa de todos os endpoints da API RESTful.

## ✅ Critérios de Aceitação
- [ ] Documentação de cada endpoint (método, path, descrição)
- [ ] Exemplos de requisições (body, headers)
- [ ] Exemplos de respostas (sucesso e erro)
- [ ] Códigos de status HTTP explicados
- [ ] Formato dos DTOs documentado
- [ ] Pode ser em Markdown ou Swagger/OpenAPI

## 📊 Estimativa
3 pontos
```

---

### TASK-025: Criar guia de instalação e execução

**Labels:** `priority: high`, `épico: documentação`, `pontos: 2`  
**Milestone:** Sprint 4 - Documentação e Entrega

**Descrição:**

```markdown
## 📋 Descrição
Desenvolver um guia passo a passo para configurar e executar a aplicação.

## ✅ Critérios de Aceitação
- [ ] Pré-requisitos listados (Java, Docker, Maven/Gradle)
- [ ] Instruções de clone do repositório
- [ ] Instruções de build da aplicação
- [ ] Instruções de execução com Docker Compose (local)
- [ ] Instruções de execução local (sem Docker)
- [ ] Instruções de acesso à aplicação em produção (Render.com)
- [ ] Comandos para testar se está funcionando
- [ ] Troubleshooting de problemas comuns

## 📊 Estimativa
2 pontos
```

---

### TASK-026: Implementar Swagger/OpenAPI (opcional)

**Labels:** `priority: low`, `épico: documentação`, `pontos: 2`  
**Milestone:** Sprint 4 - Documentação e Entrega

**Descrição:**

```markdown
## 📋 Descrição
Adicionar documentação interativa da API utilizando Swagger/OpenAPI.

## ✅ Critérios de Aceitação
- [ ] Dependência Springdoc OpenAPI adicionada
- [ ] Swagger UI acessível via /swagger-ui.html
- [ ] Todos os endpoints documentados automaticamente
- [ ] Exemplos de requisições configurados
- [ ] Descrições customizadas adicionadas

## 📊 Estimativa
2 pontos
```

---

## ÉPICO 10: Finalização

### TASK-027: Configurar repositório Git

**Labels:** `priority: high`, `épico: finalização`, `pontos: 1`  
**Milestone:** Sprint 4 - Documentação e Entrega

**Descrição:**

```markdown
## 📋 Descrição
Configurar o repositório Git com boas práticas e preparar para publicação.

## ✅ Critérios de Aceitação
- [ ] Repositório criado no GitHub/GitLab
- [ ] .gitignore configurado corretamente
- [ ] Commits com mensagens descritivas
- [ ] Branch main/master protegida
- [ ] README.md na raiz do projeto

## 📊 Estimativa
1 ponto
```

---

### TASK-028: Realizar testes finais de integração

**Labels:** `priority: high`, `épico: finalização`, `pontos: 3`  
**Milestone:** Sprint 4 - Documentação e Entrega

**Descrição:**

```markdown
## 📋 Descrição
Executar testes completos de todos os fluxos da aplicação para garantir funcionamento correto.

## ✅ Critérios de Aceitação
- [ ] Todos os endpoints testados via Postman/Insomnia
- [ ] Fluxo completo de CRUD testado
- [ ] Validação de login testada
- [ ] Aplicação rodando via Docker Compose
- [ ] Persistência de dados funcionando
- [ ] Tratamento de erros validado
- [ ] Performance aceitável

## 📊 Estimativa
3 pontos
```

---

### TASK-029: Revisar código e aplicar boas práticas

**Labels:** `priority: medium`, `épico: finalização`, `pontos: 2`  
**Milestone:** Sprint 4 - Documentação e Entrega

**Descrição:**

```markdown
## 📋 Descrição
Revisar todo o código-fonte aplicando boas práticas de desenvolvimento e clean code.

## ✅ Critérios de Aceitação
- [ ] Código formatado consistentemente
- [ ] Nomenclaturas seguindo convenções Java
- [ ] Comentários apenas onde necessário
- [ ] Código sem warnings
- [ ] Princípios SOLID aplicados
- [ ] DRY (Don't Repeat Yourself) respeitado
- [ ] Código legível e manutenível

## 📊 Estimativa
2 pontos
```

---

### TASK-030: Preparar entrega final

**Labels:** `priority: high`, `épico: finalização`, `pontos: 2`  
**Milestone:** Sprint 4 - Documentação e Entrega

**Descrição:**

```markdown
## 📋 Descrição
Preparar todos os artefatos necessários para entrega do projeto.

## ✅ Critérios de Aceitação
- [ ] Repositório público no GitHub/GitLab
- [ ] README.md completo e atualizado
- [ ] Collection do Postman incluída no repositório
- [ ] Docker Compose funcional
- [ ] Aplicação deployada no Render.com
- [ ] URL pública da aplicação no README
- [ ] Documentação completa
- [ ] Código-fonte organizado
- [ ] Link do repositório pronto para envio

## 📊 Estimativa
2 pontos
```

---

### TASK-031: Deploy no Render.com

**Labels:** `priority: high`, `épico: finalização`, `pontos: 3`  
**Milestone:** Sprint 4 - Documentação e Entrega

**Descrição:**

```markdown
## 📋 Descrição
Realizar o deploy da aplicação no Render.com para disponibilizar em produção com URL pública.

## ✅ Critérios de Aceitação
- [ ] Conta criada no Render.com
- [ ] Repositório conectado ao Render
- [ ] Web Service criado com Dockerfile
- [ ] PostgreSQL gratuito provisionado no Render
- [ ] Variáveis de ambiente configuradas (DATABASE_URL, etc)
- [ ] Build e deploy executados com sucesso
- [ ] Aplicação acessível via URL pública
- [ ] Health check configurado
- [ ] Logs de aplicação acessíveis
- [ ] Testes dos endpoints em produção realizados
- [ ] URL pública documentada no README

## 🔧 Dependências Técnicas
- TASK-018 (Dockerfile) concluída
- TASK-028 (Testes finais) concluída
- Repositório no GitHub

## 📊 Estimativa
3 pontos
```

---

## 🎯 Resumo Rápido

**Total:** 31 Issues

-   **Alta Prioridade:** 21 issues
-   **Média Prioridade:** 7 issues
-   **Baixa Prioridade:** 3 issues

**Por Sprint:**

-   Sprint 1: 7 issues (20 pontos)
-   Sprint 2: 10 issues (22 pontos)
-   Sprint 3: 5 issues (16 pontos)
-   Sprint 4: 9 issues (21 pontos)

---

**💡 Dica:** Copie e cole cada template ao criar as issues no GitHub. Os checkboxes funcionarão automaticamente!
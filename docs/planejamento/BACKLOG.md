# BACKLOG - Tech Challenge Fase 1
## Sistema de Gerenciamento de Usuários

---

## 📋 ÉPICO 1: Configuração Inicial do Projeto

### TASK-001: Criar estrutura inicial do projeto Spring Boot
**Prioridade:** Alta  
**Estimativa:** 2 pontos

**Descrição:**  
Configurar o projeto Spring Boot utilizando o Spring Initializr com as dependências necessárias para desenvolvimento da API RESTful.

**Critérios de Aceitação:**
- Projeto criado via Spring Initializr
- Dependências incluídas: Spring Web, Spring Data JPA, Spring Validation, Driver do banco de dados
- Estrutura de pacotes criada (controller, service, repository, model, dto)
- Projeto compila sem erros
- Application.properties configurado com propriedades básicas

**Dependências Técnicas:**
- Java 17+
- Spring Boot 3.x
- Maven ou Gradle

---

### TASK-002: Configurar banco de dados relacional
**Prioridade:** Alta  
**Estimativa:** 3 pontos

**Descrição:**  
Configurar o banco de dados relacional (PostgreSQL ou MySQL) com as configurações necessárias para desenvolvimento e produção.

**Critérios de Aceitação:**
- Banco de dados escolhido e justificado
- Configurações de conexão no application.properties
- Configuração de profiles (dev, prod)
- Script de inicialização do banco (se necessário)
- Conexão testada e funcionando

---

## 📋 ÉPICO 2: Desenvolvimento da Entidade e Camada de Dados

### TASK-003: Criar entidade Usuario (User)
**Prioridade:** Alta  
**Estimativa:** 3 pontos

**Descrição:**  
Desenvolver a entidade Usuario com todos os campos obrigatórios e anotações JPA necessárias.

**Critérios de Aceitação:**
- Entidade Usuario criada com os campos: id, nome, email, login, senha, dataUltimaAlteracao
- Anotações JPA configuradas (@Entity, @Table, @Id, @GeneratedValue)
- Validações implementadas (@NotNull, @Email, @Size)
- Campo dataUltimaAlteracao com atualização automática (@PreUpdate, @PrePersist)
- Getters, setters, construtores implementados
- Equals e hashCode baseados no ID

---

### TASK-004: Criar repository para Usuario
**Prioridade:** Alta  
**Estimativa:** 2 pontos

**Descrição:**  
Implementar a interface repository utilizando Spring Data JPA para operações de banco de dados.

**Critérios de Aceitação:**
- Interface UsuarioRepository criada estendendo JpaRepository
- Método customizado para buscar usuário por login
- Método customizado para buscar usuário por email
- Método customizado para validar login (findByLoginAndSenha)
- Queries funcionando corretamente

---

## 📋 ÉPICO 3: Desenvolvimento da Camada de Serviço

### TASK-005: Criar DTOs (Data Transfer Objects)
**Prioridade:** Alta  
**Estimativa:** 2 pontos

**Descrição:**  
Criar os DTOs necessários para requisições e respostas da API, separando a camada de apresentação da camada de domínio.

**Critérios de Aceitação:**
- UsuarioRequestDTO criado (para cadastro/atualização)
- UsuarioResponseDTO criado (para retorno de dados)
- LoginRequestDTO criado (para validação de login)
- LoginResponseDTO criado (para resposta de login)
- Validações Bean Validation nos DTOs de request
- Senha não exposta nos DTOs de response

---

### TASK-006: Implementar service de gerenciamento de usuários
**Prioridade:** Alta  
**Estimativa:** 5 pontos

**Descrição:**  
Desenvolver a camada de serviço com toda a lógica de negócio para gerenciamento de usuários.

**Critérios de Aceitação:**
- UsuarioService criado com anotação @Service
- Método criarUsuario implementado
- Método atualizarUsuario implementado
- Método buscarUsuarioPorId implementado
- Método listarTodosUsuarios implementado
- Método deletarUsuario implementado
- Validações de negócio implementadas (email único, login único)
- Tratamento de exceções customizadas
- Senha criptografada antes de salvar (BCrypt)

---

### TASK-007: Implementar validação de login
**Prioridade:** Alta  
**Estimativa:** 3 pontos

**Descrição:**  
Criar a funcionalidade de validação de credenciais de login do usuário.

**Critérios de Aceitação:**
- Método validarLogin no UsuarioService
- Comparação de senha utilizando BCrypt
- Retorno de mensagem de sucesso quando credenciais válidas
- Retorno de mensagem de erro quando credenciais inválidas
- Não expor informações sensíveis em caso de erro
- Logs de tentativas de login

---

## 📋 ÉPICO 4: Desenvolvimento da API RESTful

### TASK-008: Criar endpoint de cadastro de usuário (POST)
**Prioridade:** Alta  
**Estimativa:** 3 pontos

**Descrição:**  
Implementar o endpoint REST para criação de novos usuários no sistema.

**Critérios de Aceitação:**
- Endpoint POST /api/usuarios criado
- Recebe UsuarioRequestDTO no body
- Retorna UsuarioResponseDTO com status 201 Created
- Validações de entrada funcionando
- Tratamento de erros (email/login duplicado)
- Location header com URI do recurso criado

---

### TASK-009: Criar endpoint de atualização de usuário (PUT)
**Prioridade:** Alta  
**Estimativa:** 3 pontos

**Descrição:**  
Implementar o endpoint REST para atualização de dados de usuários existentes.

**Critérios de Aceitação:**
- Endpoint PUT /api/usuarios/{id} criado
- Recebe UsuarioRequestDTO no body
- Retorna UsuarioResponseDTO com status 200 OK
- Campo dataUltimaAlteracao atualizado automaticamente
- Validação se usuário existe
- Tratamento de erros apropriado

---

### TASK-010: Criar endpoint de exclusão de usuário (DELETE)
**Prioridade:** Alta  
**Estimativa:** 2 pontos

**Descrição:**  
Implementar o endpoint REST para exclusão de usuários pelo ID.

**Critérios de Aceitação:**
- Endpoint DELETE /api/usuarios/{id} criado
- Retorna status 204 No Content quando sucesso
- Retorna status 404 Not Found quando usuário não existe
- Exclusão física do registro no banco
- Logs da operação de exclusão

---

### TASK-011: Criar endpoint de busca de usuário por ID (GET)
**Prioridade:** Média  
**Estimativa:** 2 pontos

**Descrição:**  
Implementar o endpoint REST para buscar um usuário específico pelo ID.

**Critérios de Aceitação:**
- Endpoint GET /api/usuarios/{id} criado
- Retorna UsuarioResponseDTO com status 200 OK
- Retorna status 404 Not Found quando usuário não existe
- Senha não exposta na resposta

---

### TASK-012: Criar endpoint de listagem de usuários (GET)
**Prioridade:** Média  
**Estimativa:** 2 pontos

**Descrição:**  
Implementar o endpoint REST para listar todos os usuários cadastrados.

**Critérios de Aceitação:**
- Endpoint GET /api/usuarios criado
- Retorna lista de UsuarioResponseDTO com status 200 OK
- Senhas não expostas na resposta
- Retorna lista vazia quando não há usuários

---

### TASK-013: Criar endpoint de validação de login (POST)
**Prioridade:** Alta  
**Estimativa:** 3 pontos

**Descrição:**  
Implementar o endpoint REST para validação de credenciais de login.

**Critérios de Aceitação:**
- Endpoint POST /api/usuarios/login criado
- Recebe LoginRequestDTO no body
- Retorna LoginResponseDTO com mensagem de sucesso (status 200)
- Retorna status 401 Unauthorized com mensagem quando credenciais inválidas
- Não expõe informações sensíveis em caso de erro

---

## 📋 ÉPICO 5: Tratamento de Erros e Validações

### TASK-014: Implementar exception handler global
**Prioridade:** Alta  
**Estimativa:** 3 pontos

**Descrição:**  
Criar um controlador global de exceções para padronizar as respostas de erro da API.

**Critérios de Aceitação:**
- @RestControllerAdvice criado
- Tratamento de MethodArgumentNotValidException
- Tratamento de exceções customizadas (UsuarioNotFoundException, etc)
- Tratamento de DataIntegrityViolationException
- Resposta de erro padronizada com timestamp, status, mensagem e path
- Logs de erros apropriados

---

### TASK-015: Criar exceções customizadas
**Prioridade:** Média  
**Estimativa:** 2 pontos

**Descrição:**  
Desenvolver exceções customizadas para situações específicas do domínio da aplicação.

**Critérios de Aceitação:**
- UsuarioNotFoundException criada
- EmailJaCadastradoException criada
- LoginJaCadastradoException criada
- CredenciaisInvalidasException criada
- Exceções estendem RuntimeException
- Mensagens descritivas

---

## 📋 ÉPICO 6: Segurança

### TASK-016: Implementar criptografia de senha
**Prioridade:** Alta  
**Estimativa:** 2 pontos

**Descrição:**  
Configurar e implementar criptografia de senhas utilizando BCrypt.

**Critérios de Aceitação:**
- Dependência Spring Security adicionada (ou BCrypt standalone)
- PasswordEncoder configurado como Bean
- Senhas criptografadas antes de salvar no banco
- Validação de senha utilizando BCrypt na autenticação
- Senhas nunca armazenadas em texto plano

---

### TASK-017: Implementar validações de segurança
**Prioridade:** Média  
**Estimativa:** 2 pontos

**Descrição:**  
Adicionar validações de segurança para campos sensíveis e regras de negócio.

**Critérios de Aceitação:**
- Validação de força de senha (mínimo 8 caracteres)
- Validação de formato de email
- Validação de unicidade de login e email
- Sanitização de inputs
- Proteção contra SQL Injection (via JPA)

---

## 📋 ÉPICO 7: Containerização com Docker

### TASK-018: Criar Dockerfile para aplicação Spring Boot
**Prioridade:** Alta  
**Estimativa:** 3 pontos

**Descrição:**  
Desenvolver o Dockerfile para containerizar a aplicação Spring Boot.

**Critérios de Aceitação:**
- Dockerfile criado na raiz do projeto
- Utiliza imagem base Java apropriada
- Multi-stage build implementado (build + runtime)
- Porta da aplicação exposta
- JAR da aplicação copiado corretamente
- Imagem otimizada (tamanho reduzido)
- Aplicação inicia corretamente no container

---

### TASK-019: Criar docker-compose.yml
**Prioridade:** Alta  
**Estimativa:** 4 pontos

**Descrição:**  
Configurar o Docker Compose para orquestrar a aplicação e o banco de dados.

**Critérios de Aceitação:**
- docker-compose.yml criado na raiz do projeto
- Serviço da aplicação Spring Boot configurado
- Serviço do banco de dados configurado
- Network configurada entre os serviços
- Volumes configurados para persistência de dados
- Variáveis de ambiente configuradas
- Health checks implementados
- Ordem de inicialização correta (depends_on)
- Aplicação e banco sobem com um único comando

**Nota:**
- Docker Compose é para execução local
- Para produção, será usado Render.com (TASK-031)

---

### TASK-020: Criar arquivo .dockerignore
**Prioridade:** Baixa  
**Estimativa:** 1 ponto

**Descrição:**  
Criar arquivo .dockerignore para otimizar o build da imagem Docker.

**Critérios de Aceitação:**
- .dockerignore criado na raiz do projeto
- Arquivos desnecessários excluídos (target/, .git/, .idea/, etc)
- Build da imagem mais rápido
- Tamanho da imagem reduzido

---

## 📋 ÉPICO 8: Testes

### TASK-021: Criar collection do Postman/Insomnia
**Prioridade:** Alta  
**Estimativa:** 3 pontos

**Descrição:**  
Desenvolver uma collection completa de testes para todos os endpoints da API.

**Critérios de Aceitação:**
- Collection criada com todos os endpoints
- Testes para cenários de sucesso
- Testes para cenários de erro
- Variáveis de ambiente configuradas
- Exemplos de requisições e respostas
- Arquivo JSON da collection exportado
- Documentação de como importar e usar

---

### TASK-022: Criar testes unitários (opcional mas recomendado)
**Prioridade:** Baixa  
**Estimativa:** 5 pontos

**Descrição:**  
Implementar testes unitários para as camadas de service e repository.

**Critérios de Aceitação:**
- Testes para UsuarioService criados
- Testes para UsuarioRepository criados
- Cobertura mínima de 70%
- Utilização de JUnit 5 e Mockito
- Testes passando com sucesso

---

## 📋 ÉPICO 9: Documentação

### TASK-023: Documentar arquitetura do projeto
**Prioridade:** Alta  
**Estimativa:** 3 pontos

**Descrição:**  
Criar documentação detalhada da arquitetura do projeto, explicando as camadas e decisões técnicas.

**Critérios de Aceitação:**
- README.md criado com visão geral do projeto
- Diagrama de arquitetura (camadas)
- Descrição das tecnologias utilizadas
- Justificativa das escolhas técnicas
- Estrutura de pacotes explicada
- Padrões de projeto utilizados documentados
- Seção sobre deploy no Render.com
- Arquitetura de produção vs desenvolvimento explicada

---

### TASK-024: Documentar endpoints da API
**Prioridade:** Alta  
**Estimativa:** 3 pontos

**Descrição:**  
Criar documentação completa de todos os endpoints da API RESTful.

**Critérios de Aceitação:**
- Documentação de cada endpoint (método, path, descrição)
- Exemplos de requisições (body, headers)
- Exemplos de respostas (sucesso e erro)
- Códigos de status HTTP explicados
- Formato dos DTOs documentado
- Pode ser em Markdown ou Swagger/OpenAPI

---

### TASK-025: Criar guia de instalação e execução
**Prioridade:** Alta  
**Estimativa:** 2 pontos

**Descrição:**  
Desenvolver um guia passo a passo para configurar e executar a aplicação.

**Critérios de Aceitação:**
- Pré-requisitos listados (Java, Docker, Maven/Gradle)
- Instruções de clone do repositório
- Instruções de build da aplicação
- Instruções de execução com Docker Compose (local)
- Instruções de execução local (sem Docker)
- Instruções de acesso à aplicação em produção (Render.com)
- Comandos para testar se está funcionando
- Troubleshooting de problemas comuns

---

### TASK-026: Implementar Swagger/OpenAPI (opcional)
**Prioridade:** Baixa  
**Estimativa:** 2 pontos

**Descrição:**  
Adicionar documentação interativa da API utilizando Swagger/OpenAPI.

**Critérios de Aceitação:**
- Dependência Springdoc OpenAPI adicionada
- Swagger UI acessível via /swagger-ui.html
- Todos os endpoints documentados automaticamente
- Exemplos de requisições configurados
- Descrições customizadas adicionadas

---

## 📋 ÉPICO 10: Finalização e Entrega

### TASK-031: Deploy no Render.com
**Prioridade:** Alta  
**Estimativa:** 3 pontos

**Descrição:**  
Realizar o deploy da aplicação no Render.com para disponibilizar em produção com URL pública.

**Critérios de Aceitação:**
- Conta criada no Render.com
- Repositório conectado ao Render
- Web Service criado com Dockerfile
- PostgreSQL gratuito provisionado no Render
- Variáveis de ambiente configuradas (DATABASE_URL, etc)
- Build e deploy executados com sucesso
- Aplicação acessível via URL pública
- Health check configurado
- Logs de aplicação acessíveis
- Testes dos endpoints em produção realizados
- URL pública documentada no README

**Dependências Técnicas:**
- TASK-018 (Dockerfile) concluída
- TASK-028 (Testes finais) concluída
- Repositório no GitHub

---

### TASK-027: Configurar repositório Git
**Prioridade:** Alta  
**Estimativa:** 1 ponto

**Descrição:**  
Configurar o repositório Git com boas práticas e preparar para publicação.

**Critérios de Aceitação:**
- Repositório criado no GitHub/GitLab
- .gitignore configurado corretamente
- Commits com mensagens descritivas
- Branch main/master protegida
- README.md na raiz do projeto

---

### TASK-028: Realizar testes finais de integração
**Prioridade:** Alta  
**Estimativa:** 3 pontos

**Descrição:**  
Executar testes completos de todos os fluxos da aplicação para garantir funcionamento correto.

**Critérios de Aceitação:**
- Todos os endpoints testados via Postman/Insomnia
- Fluxo completo de CRUD testado
- Validação de login testada
- Aplicação rodando via Docker Compose
- Persistência de dados funcionando
- Tratamento de erros validado
- Performance aceitável

---

### TASK-029: Revisar código e aplicar boas práticas
**Prioridade:** Média  
**Estimativa:** 2 pontos

**Descrição:**  
Revisar todo o código-fonte aplicando boas práticas de desenvolvimento e clean code.

**Critérios de Aceitação:**
- Código formatado consistentemente
- Nomenclaturas seguindo convenções Java
- Comentários apenas onde necessário
- Código sem warnings
- Princípios SOLID aplicados
- DRY (Don't Repeat Yourself) respeitado
- Código legível e manutenível

---

### TASK-030: Preparar entrega final
**Prioridade:** Alta  
**Estimativa:** 2 pontos

**Descrição:**  
Preparar todos os artefatos necessários para entrega do projeto.

**Critérios de Aceitação:**
- Repositório público no GitHub/GitLab
- README.md completo e atualizado
- Collection do Postman incluída no repositório
- Docker Compose funcional
- Aplicação deployada no Render.com
- URL pública da aplicação no README
- Documentação completa
- Código-fonte organizado
- Link do repositório pronto para envio

---

## 📊 RESUMO DO BACKLOG

**Total de Tasks:** 31  
**Estimativa Total:** 79 pontos

### Por Prioridade:
- **Alta:** 21 tasks
- **Média:** 7 tasks
- **Baixa:** 3 tasks

### Por Épico:
1. Configuração Inicial: 2 tasks (5 pontos)
2. Entidade e Dados: 2 tasks (5 pontos)
3. Camada de Serviço: 3 tasks (10 pontos)
4. API RESTful: 6 tasks (15 pontos)
5. Tratamento de Erros: 2 tasks (5 pontos)
6. Segurança: 2 tasks (4 pontos)
7. Docker: 3 tasks (8 pontos)
8. Testes: 2 tasks (8 pontos)
9. Documentação: 4 tasks (10 pontos)
10. Finalização: 5 tasks (11 pontos)

---

## 🎯 ORDEM SUGERIDA DE EXECUÇÃO

**Sprint 1 - Fundação (Tasks 1-7):**
- Configuração inicial e estrutura base
- Entidade, repository e service
- Validação de login

**Sprint 2 - API e Segurança (Tasks 8-17):**
- Todos os endpoints REST
- Tratamento de erros
- Segurança e criptografia

**Sprint 3 - Docker e Testes (Tasks 18-22):**
- Containerização completa
- Collections de teste

**Sprint 4 - Documentação e Entrega (Tasks 23-31):**
- Documentação completa
- Deploy no Render.com
- Testes finais
- Preparação para entrega

---

## 📝 NOTAS

- As estimativas são em pontos de história (Story Points)
- 1 ponto ≈ 1-2 horas de trabalho
- Prioridades podem ser ajustadas conforme necessidade
- Tasks podem ser quebradas em subtasks menores se necessário
- Recomenda-se seguir a ordem sugerida para evitar dependências bloqueantes

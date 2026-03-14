# Contexto do Projeto - FIAP Tech Challenge Fase 1

## Stack
- Java 17 (CI/CD) | Java 25.0.2 (local - JaCoCo incompatível)
- Spring Boot 3.2.3
- PostgreSQL (prod/dev) | H2 (test - ainda não configurado)
- Maven, Docker, GitHub Actions, SonarCloud, Render.com

## Repositório
- URL: https://github.com/jeffesa/12ADJT-fase-1.git
- Branch principal: main / develop
- Branch protection ativa em main e develop (PR obrigatório, status check obrigatório)

## SonarCloud
- Organização: jeffesa
- Project key: jeffesa_12ADJT-fase-1
- Token: secret SONAR_TOKEN no GitHub Actions

## CI/CD
- pr-validation.yml: build-and-test + sonarcloud em PRs para main/develop
- sonarcloud.yml: push/PR para main
- pre-push hook: roda mvn test antes de qualquer push local

## Estratégia de Merge
- Manter as 3 opções habilitadas no GitHub (merge commit, squash, rebase)
- Convenção do time: usar squash por padrão (1 commit por propósito/branch)
- Prefixos de commit: feat:, fix:, ci:, test:, docs:, refactor:
- Cada branch deve ter um único propósito para o squash fazer sentido

## Boa Prática - Antes de Abrir PR
Sempre rodar `mvn test` localmente antes de commitar para evitar bloqueio no pipeline:
```bash
mvn test
```
Se passar localmente, o pipeline do GitHub Actions também passa.
O pre-push hook já faz isso automaticamente — nunca usar `--no-verify` em branches reais.

## Status das Tasks
- ✅ TASK-001: Estrutura inicial, SecurityConfig, profiles dev/prod, Maven
- ✅ README.md: Guia completo de instalação e configuração
- ✅ CI/CD: GitHub Actions configurado com SonarCloud
- ✅ Branch protection: main e develop
- ✅ Git hook pre-push: bloqueia push se testes falharem
- ✅ TASK-simulacao: Vulnerabilidade log4j CVE-2021-44228 simulada e validada no pipeline
- ⏳ TASK-002: Configurar H2/test para testes locais
- ⏳ Endpoints REST completos
- ⏳ Testes unitários service/controller

## Simulação TASK-simulacao (concluída)
- Adicionada dependência log4j-core 2.14.1 (CVE-2021-44228) no pom.xml
- Teste `deveDetectarDependenciaVulneravel` falhava intencionalmente (2.14.1 vs 2.17.1)
- Pipeline bloqueou o PR corretamente — validação do fluxo completa
- Removida a vulnerabilidade após validação, pipeline voltou a passar

## Insights Chave
- Maven do IntelliJ: /Applications/IntelliJ IDEA.app/Contents/plugins/maven/lib/maven3/bin/mvn (no PATH via ~/.zshrc)
- Testes locais falham com erro de conexão PostgreSQL (porta 5432 recusada) - application-test.properties não existe ainda
- --no-verify usado apenas para testes de pipeline, nunca em branches reais
- JaCoCo 0.8.11 incompatível com Java 25 localmente, funciona no CI com Java 17

## Convenções do Projeto
- Idioma: português em todas as interações e commits
- Pacote base: com.fiap.fase1
- Endpoints base: /api/usuarios
- Profiles: dev, prod, test

## Estrutura Principal
- src/main/java/com/fiap/fase1/controller/
- src/main/java/com/fiap/fase1/service/
- src/main/java/com/fiap/fase1/repository/
- src/main/java/com/fiap/fase1/model/
- src/main/java/com/fiap/fase1/dto/
- src/main/java/com/fiap/fase1/exception/
- src/main/java/com/fiap/fase1/config/

# 🚀 Sistema de Gerenciamento de Usuários

[![CI/CD Pipeline](https://github.com/jeffesa/12ADJT-fase-1/actions/workflows/sonarcloud.yml/badge.svg)](https://github.com/jeffesa/12ADJT-fase-1/actions/workflows/sonarcloud.yml)

Sistema de gerenciamento de usuários desenvolvido com Spring Boot para o Tech Challenge - Fase 1 da FIAP.

---

## 📋 Sobre o Projeto

API RESTful para gerenciamento de usuários com funcionalidades de:

-   ✅ Cadastro de usuários
-   ✅ Atualização de dados
-   ✅ Exclusão de usuários
-   ✅ Validação de login
-   ✅ Listagem de usuários

---

## 🛠️ Tecnologias

-   **Java 17**
-   **Spring Boot 3.2.3**
-   **Spring Data JPA**
-   **PostgreSQL**
-   **Docker & Docker Compose**
-   **Maven**
-   **Swagger/OpenAPI**
-   **JUnit 5**

---

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/fiap/fase1/
│   │   ├── controller/      # Controllers REST
│   │   ├── service/         # Lógica de negócio
│   │   ├── repository/      # Acesso a dados
│   │   ├── model/           # Entidades JPA
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── exception/       # Exceções customizadas
│   │   └── config/          # Configurações
│   └── resources/
│       ├── application.properties
│       ├── application-dev.properties
│       └── application-prod.properties
└── test/                    # Testes unitários
```

---

## 🚀 Instalação e Configuração

### Pré-requisitos

#### Requisitos Obrigatórios

Software

Versão Mínima

Versão Recomendada

Download

**Java JDK**

17

17 LTS

[Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) ou [OpenJDK](https://adoptium.net/)

**Maven**

3.8+

3.9+

[Apache Maven](https://maven.apache.org/download.cgi)

**PostgreSQL**

14+

15+

[PostgreSQL](https://www.postgresql.org/download/)

**Git**

2.30+

Latest

[Git](https://git-scm.com/downloads)

#### Requisitos Opcionais

Software

Versão

Finalidade

**Docker**

20.10+

Containerização

**Docker Compose**

2.0+

Orquestração de containers

**IntelliJ IDEA**

2023.1+

IDE recomendada

**Postman**

Latest

Testes de API

---

### 📦 Instalação do Ambiente

#### macOS

```bash
# Instalar Homebrew (se não tiver)
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Instalar Java 17
brew install openjdk@17

# Configurar JAVA_HOME
echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 17)' >> ~/.zshrc
echo 'export PATH="$JAVA_HOME/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc

# Instalar Maven
brew install maven

# Instalar PostgreSQL
brew install postgresql@15
brew services start postgresql@15

# Instalar Docker (opcional)
brew install --cask docker
```

#### Linux (Ubuntu/Debian)

```bash
# Atualizar repositórios
sudo apt update

# Instalar Java 17
sudo apt install openjdk-17-jdk -y

# Configurar JAVA_HOME
echo 'export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64' >> ~/.bashrc
echo 'export PATH="$JAVA_HOME/bin:$PATH"' >> ~/.bashrc
source ~/.bashrc

# Instalar Maven
sudo apt install maven -y

# Instalar PostgreSQL
sudo apt install postgresql postgresql-contrib -y
sudo systemctl start postgresql
sudo systemctl enable postgresql

# Instalar Docker (opcional)
sudo apt install docker.io docker-compose -y
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker $USER
```

#### Windows

1.  **Java 17**:
    
    -   Baixe o instalador do [Adoptium](https://adoptium.net/)
    -   Execute o instalador e marque "Set JAVA_HOME variable"
    -   Adicione `%JAVA_HOME%bin` ao PATH
2.  **Maven**:
    
    -   Baixe do [site oficial](https://maven.apache.org/download.cgi)
    -   Extraia para `C:Program FilesApachemaven`
    -   Adicione `C:Program FilesApachemavenbin` ao PATH
3.  **PostgreSQL**:
    
    -   Baixe o instalador do [site oficial](https://www.postgresql.org/download/windows/)
    -   Execute e configure senha do usuário postgres
4.  **Docker Desktop** (opcional):
    
    -   Baixe do [site oficial](https://www.docker.com/products/docker-desktop)

---

### 🔧 Verificação da Instalação

```bash
# Verificar Java
java -version
# Saída esperada: openjdk version "17.x.x"

# Verificar Maven
mvn -version
# Saída esperada: Apache Maven 3.8.x ou superior

# Verificar PostgreSQL
psql --version
# Saída esperada: psql (PostgreSQL) 14.x ou superior

# Verificar Docker (opcional)
docker --version
docker-compose --version
```

---

### 🎯 Setup do Projeto

#### 1. Clonar o Repositório

```bash
git clone https://github.com/jeffesa/12ADJT-fase-1.git
cd 12ADJT-fase-1
```

#### 2. Configurar Banco de Dados

##### Opção A: PostgreSQL Local

```bash
# Criar usuário e banco de dados
psql -U postgres

CREATE USER fase1_user WITH PASSWORD 'fase1_password';
CREATE DATABASE usuarios_db OWNER fase1_user;
GRANT ALL PRIVILEGES ON DATABASE usuarios_db TO fase1_user;
q
```

##### Opção B: Docker Compose (Recomendado)

```bash
# Subir PostgreSQL via Docker
docker-compose up -d postgres

# Verificar se está rodando
docker-compose ps
```

#### 3. Configurar Variáveis de Ambiente (Opcional)

Crie um arquivo `.env` na raiz do projeto:

```env
# Database
DB_HOST=localhost
DB_PORT=5432
DB_NAME=usuarios_db
DB_USER=fase1_user
DB_PASSWORD=fase1_password

# Application
SPRING_PROFILES_ACTIVE=dev
SERVER_PORT=8080
```

#### 4. Compilar o Projeto

```bash
# Limpar e compilar
mvn clean install

# Ou pular os testes
mvn clean install -DskipTests
```

#### 5. Executar a Aplicação

```bash
# Usando o script run.sh (recomendado)
./run.sh          # profile test (H2 em memória, padrão)
# ./run.sh dev    # profile dev (PostgreSQL local - requer Docker)
# ./run.sh prod   # profile prod (requer configuração de produção)

# Ou manualmente com Maven
mvn spring-boot:run -Dspring-boot.run.profiles=test
```

> O `run.sh` detecta automaticamente o Java 17, mata processos na porta 8080 se necessário e inicia a aplicação com o profile escolhido.

A aplicação estará disponível em: **[http://localhost:8080](http://localhost:8080)**

---

### 🔨 Configuração do IntelliJ IDEA

#### 1. Importar Projeto

1.  Abra o IntelliJ IDEA
2.  **File → Open**
3.  Selecione a pasta do projeto
4.  Aguarde a indexação do Maven

#### 2. Configurar JDK

1.  **File → Project Structure** (⌘;)
2.  **Project**:
    -   SDK: **17** (Oracle OpenJDK 17 ou Temurin 17)
    -   Language level: **17 - Sealed types, always-strict floating-point semantics**
3.  **Modules**:
    -   Language level: **17**
4.  Clique **OK**

#### 3. Configurar Maven

1.  **IntelliJ IDEA → Settings** (⌘,)
2.  **Build, Execution, Deployment → Build Tools → Maven**
3.  Configurar:
    -   Maven home directory: `/Applications/IntelliJ IDEA.app/Contents/plugins/maven/lib/maven3` (macOS)
    -   User settings file: `~/.m2/settings.xml`
    -   Local repository: `~/.m2/repository`
4.  Clique **OK**

> ⚠️ **Importante:** O projeto requer **Java 17** para rodar os testes localmente. O JaCoCo 0.8.11 é incompatível com Java 21+. Certifique-se de que o JDK configurado no Maven Runner também é o 17:
> **Settings → Build, Execution, Deployment → Build Tools → Maven → Runner → JRE: 17**

#### 4. Habilitar Annotation Processing

1.  **Settings → Build, Execution, Deployment → Compiler → Annotation Processors**
2.  Marque **Enable annotation processing**
3.  Clique **OK**

#### 5. Configurar Run Configuration

1.  **Run → Edit Configurations**
2.  Clique **+** → **Spring Boot**
3.  Configure:
    -   Name: `Fase1SpringExplorerApplication`
    -   Main class: `com.fiap.fase1.Fase1SpringExplorerApplication`
    -   Active profiles: `dev`
    -   JRE: **17**
4.  Clique **OK**

---

### 🐛 Troubleshooting

#### Erro: "package org.junit.jupiter.api does not exist"

**Solução:**

```bash
# Limpar cache do Maven
mvn dependency:purge-local-repository -DreResolve=false
mvn clean install

# No IntelliJ: File → Invalidate Caches → Invalidate and Restart
```

#### Erro: "Unsupported class file major version" no JaCoCo

**Causa:** JaCoCo 0.8.11 é incompatível com Java 21+

**Solução:**

-   Configure o Maven Runner para usar Java 17: **Settings → Build Tools → Maven → Runner → JRE: 17**
-   Ou rode os testes pulando o JaCoCo: `mvn test -Djacoco.skip=true`

#### Erro: "java.lang.ExceptionInInitializerError"

**Causa:** Incompatibilidade de versão do Java

**Solução:**

-   Verifique se está usando Java 17: `java -version`
-   Configure o JDK 17 no IntelliJ (Project Structure)

#### Erro: "Connection refused" ao conectar no PostgreSQL

**Solução:**

```bash
# Verificar se PostgreSQL está rodando
# macOS
brew services list

# Linux
sudo systemctl status postgresql

# Iniciar PostgreSQL
# macOS
brew services start postgresql@15

# Linux
sudo systemctl start postgresql
```

#### Porta 8080 já está em uso

**Solução:**

```bash
# Encontrar processo usando a porta
lsof -i :8080

# Matar o processo
kill -9 <PID>

# Ou alterar a porta no application.properties
server.port=8081
```

---

## 📚 Documentação da API

### Swagger UI

Acesse a documentação interativa em:

```
http://localhost:8080/swagger-ui.html
```

### Endpoints Principais

Método

Endpoint

Descrição

POST

`/api/v1/usuarios`

Criar usuário

GET

`/api/v1/usuarios`

Listar todos

GET

`/api/v1/usuarios/{id}`

Buscar por ID

PUT

`/api/v1/usuarios/{id}`

Atualizar usuário

DELETE

`/api/v1/usuarios/{id}`

Deletar usuário

POST

`/api/v1/usuarios/login`

Validar login

### Health Check

```
GET http://localhost:8080/actuator/health
```

---

## 🐳 Docker

### Subir banco de dados para desenvolvimento

```bash
# Sobe apenas o PostgreSQL em background
docker-compose up -d postgres

# Verificar se está rodando
docker-compose ps

# Parar o banco
docker-compose down
```

Após subir o banco, rode a aplicação pelo IntelliJ com o profile `dev`.

### Build da Imagem

```bash
docker build -t fase-1-spring-explorer .
```

### Executar com Docker Compose completo

```bash
docker-compose up
```

---

## 🧪 Testes

```bash
# Executar todos os testes
mvn test

# Executar com cobertura
mvn clean test jacoco:report

# Ver relatório de cobertura
open target/site/jacoco/index.html
```

---

## 📊 CI/CD

O projeto utiliza GitHub Actions para:

-   ✅ Build automático
-   ✅ Testes automatizados
-   ✅ Análise de código (SonarCloud)
-   ✅ Deploy automático (Render.com)

---

## 🚀 Deploy

### Produção (Render.com)

A aplicação está deployada em:

```
https://fase-1-spring-explorer.onrender.com
```

---

## 📚 Documentação Completa

Toda documentação está organizada na pasta [docs/](docs/):

-   [📋 Backlog Completo](docs/planejamento/BACKLOG.md)
-   [🐙 Setup GitHub Projects](docs/github/GITHUB_PROJECTS_SETUP.md)
-   [🔄 Opções de CI/CD](docs/ci-cd/CI_CD_OPTIONS.md)
-   [🚀 Deploy no Render](docs/deploy/RENDER_DEPLOY_GUIDE.md)
-   [🔍 Setup SonarCloud](docs/qualidade/SONARCLOUD_SETUP.md)

Veja o [índice completo](docs/README.md).

---

## 👥 Autor

**Jefferson** - [GitHub](https://github.com/jeffesa)

---

## 📝 Licença

Este projeto foi desenvolvido para fins educacionais - FIAP Tech Challenge.

---

## ✅ Status do Projeto

-    Configuração inicial
-    Entidade Usuario
-    Repository e Service
-    Endpoints REST
-    Tratamento de erros
-    Segurança (BCrypt)
-    Docker
-    Testes
-    Documentação
-    Deploy

---

**Desenvolvido com ❤️ para o Tech Challenge FIAP**
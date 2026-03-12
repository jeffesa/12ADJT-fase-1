# 🚀 Guia Completo: Deploy no Render.com

Este guia mostra como fazer deploy da aplicação Spring Boot no Render.com com PostgreSQL gratuito.

---

## 🎯 Por que Render.com?

✅ **100% gratuito** para projetos pequenos  
✅ **PostgreSQL incluído** (não precisa pagar separado)  
✅ **Deploy automático** via GitHub  
✅ **HTTPS automático**  
✅ **Suporta Docker** nativamente  
✅ **Fácil configuração** (5-10 minutos)  

---

## 📋 Pré-requisitos

Antes de começar, certifique-se de ter:

- [ ] Repositório no GitHub com código completo
- [ ] Dockerfile criado e testado (TASK-018)
- [ ] Aplicação funcionando localmente
- [ ] Conta no GitHub

---

## 🚀 Passo 1: Criar Conta no Render.com

1. Acesse [render.com](https://render.com)
2. Clique em **"Get Started"**
3. Escolha **"Sign in with GitHub"**
4. Autorize o Render a acessar seus repositórios
5. Pronto! ✅

---

## 🗄️ Passo 2: Criar Banco de Dados PostgreSQL

### 2.1 Criar PostgreSQL Instance

1. No dashboard do Render, clique em **"New +"**
2. Selecione **"PostgreSQL"**
3. Preencha os dados:
   - **Name:** `fase-1-spring-explorer-db`
   - **Database:** `usuarios_db`
   - **User:** `usuarios_user` (gerado automaticamente)
   - **Region:** `Oregon (US West)` (mais próximo)
   - **PostgreSQL Version:** `16` (mais recente)
   - **Plan:** **Free** ✅

4. Clique em **"Create Database"**
5. Aguarde 2-3 minutos para provisionar

### 2.2 Copiar Credenciais

Após criado, você verá:

- **Internal Database URL** - Use esta para conectar da aplicação
- **External Database URL** - Use para conectar de ferramentas externas

**Exemplo de Internal Database URL:**
```
postgresql://usuarios_user:senha123@dpg-xxxxx-a/usuarios_db
```

⚠️ **Importante:** Guarde essa URL, vamos usar no próximo passo!

---

## 🐳 Passo 3: Criar Web Service (Aplicação)

### 3.1 Conectar Repositório

1. No dashboard do Render, clique em **"New +"**
2. Selecione **"Web Service"**
3. Clique em **"Connect a repository"**
4. Selecione seu repositório: `fase-1-spring-explorer`
5. Clique em **"Connect"**

### 3.2 Configurar Web Service

Preencha os dados:

**Basic:**
- **Name:** `fase-1-spring-explorer`
- **Region:** `Oregon (US West)` (mesma do banco)
- **Branch:** `main`
- **Runtime:** `Docker`

**Build & Deploy:**
- **Dockerfile Path:** `./Dockerfile` (padrão)

**Plan:**
- Selecione **"Free"** ✅

### 3.3 Configurar Variáveis de Ambiente

Role até **"Environment Variables"** e adicione:

| Key | Value |
|-----|-------|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://dpg-xxxxx-a/usuarios_db` |
| `SPRING_DATASOURCE_USERNAME` | `usuarios_user` |
| `SPRING_DATASOURCE_PASSWORD` | `sua_senha_aqui` |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | `update` |
| `SPRING_JPA_SHOW_SQL` | `false` |
| `SERVER_PORT` | `8080` |

⚠️ **Importante:** 
- Use a **Internal Database URL** do Passo 2.2
- Remova `postgresql://` e adicione `jdbc:postgresql://`
- Separe username e password

**Exemplo:**
```
Internal URL: postgresql://user:pass@dpg-xxxxx-a/db
```

Vira:
```
SPRING_DATASOURCE_URL: jdbc:postgresql://dpg-xxxxx-a/db
SPRING_DATASOURCE_USERNAME: user
SPRING_DATASOURCE_PASSWORD: pass
```

### 3.4 Configurar Health Check (Opcional)

Em **"Advanced"**:
- **Health Check Path:** `/actuator/health`

Para isso funcionar, adicione no `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### 3.5 Criar Web Service

1. Clique em **"Create Web Service"**
2. Aguarde o build (5-10 minutos na primeira vez)
3. Acompanhe os logs em tempo real

---

## ✅ Passo 4: Verificar Deploy

### 4.1 Aguardar Build Completar

Você verá os logs:
```
==> Building...
==> Deploying...
==> Your service is live 🎉
```

### 4.2 Acessar URL Pública

Sua aplicação estará disponível em:
```
https://fase-1-spring-explorer.onrender.com
```

### 4.3 Testar Endpoints

Teste no navegador ou Postman:

**Health Check:**
```
GET https://fase-1-spring-explorer.onrender.com/actuator/health
```

**Listar Usuários:**
```
GET https://fase-1-spring-explorer.onrender.com/api/usuarios
```

**Criar Usuário:**
```
POST https://fase-1-spring-explorer.onrender.com/api/usuarios
Content-Type: application/json

{
  "nome": "João Silva",
  "email": "joao@email.com",
  "login": "joao",
  "senha": "senha123"
}
```

---

## 🔄 Passo 5: Deploy Automático

### 5.1 Configurar Auto-Deploy

Por padrão, o Render já está configurado para:
- ✅ Deploy automático a cada push na branch `main`
- ✅ Rebuild automático quando detecta mudanças

### 5.2 Fazer Deploy de Nova Versão

```bash
# Faça suas alterações
git add .
git commit -m "feat: adiciona nova funcionalidade"
git push origin main

# Render detecta automaticamente e faz deploy!
```

### 5.3 Acompanhar Deploy

1. Acesse o dashboard do Render
2. Clique no seu Web Service
3. Veja os logs em tempo real na aba **"Logs"**

---

## 📊 Passo 6: Monitoramento

### 6.1 Ver Logs

No dashboard do Render:
- **Logs** - Logs da aplicação em tempo real
- **Events** - Histórico de deploys
- **Metrics** - CPU, memória, requests

### 6.2 Ver Status do Banco

No PostgreSQL instance:
- **Info** - Credenciais e URLs
- **Metrics** - Uso de disco, conexões
- **Logs** - Logs do PostgreSQL

---

## 🎨 Passo 7: Adicionar Badge no README

Adicione no seu `README.md`:

```markdown
## 🚀 Deploy

[![Deploy on Render](https://img.shields.io/badge/Deploy-Render-46E3B7?style=for-the-badge&logo=render&logoColor=white)](https://fase-1-spring-explorer.onrender.com)

**URL da Aplicação:** https://fase-1-spring-explorer.onrender.com

### Endpoints Disponíveis:

- `GET /api/usuarios` - Listar usuários
- `POST /api/usuarios` - Criar usuário
- `GET /api/usuarios/{id}` - Buscar usuário
- `PUT /api/usuarios/{id}` - Atualizar usuário
- `DELETE /api/usuarios/{id}` - Deletar usuário
- `POST /api/usuarios/login` - Validar login
```

---

## ⚙️ Configurações Avançadas

### Ajustar application.properties para Produção

Crie `application-prod.properties`:

```properties
# Datasource (via variáveis de ambiente)
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=false

# Logging
logging.level.root=INFO
logging.level.com.fiap=INFO

# Server
server.port=${PORT:8080}
```

### Dockerfile Otimizado para Render

```dockerfile
# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Render usa a variável PORT
EXPOSE ${PORT:-8080}

ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## 🔧 Troubleshooting

### Problema: Build falha

**Solução:**
- Verifique os logs no Render
- Certifique-se que o Dockerfile está correto
- Teste o build localmente: `docker build -t test .`

### Problema: Aplicação não conecta no banco

**Solução:**
- Verifique as variáveis de ambiente
- Use a **Internal Database URL**
- Certifique-se que o formato está correto: `jdbc:postgresql://...`

### Problema: Aplicação demora para responder

**Solução:**
- Normal no plano gratuito
- Aplicação "dorme" após 15 min de inatividade
- Primeira requisição demora ~30s para "acordar"

### Problema: Erro 502 Bad Gateway

**Solução:**
- Aguarde o deploy completar
- Verifique se a aplicação está rodando na porta correta
- Use `${PORT:8080}` no application.properties

### Problema: Banco de dados cheio

**Solução:**
- Plano gratuito tem limite de 1GB
- Limpe dados antigos
- Considere upgrade se necessário

---

## 💰 Limites do Plano Gratuito

### Web Service (Aplicação):
- ✅ 750 horas/mês
- ✅ 512 MB RAM
- ✅ 0.1 CPU
- ⚠️ Dorme após 15 min de inatividade
- ⚠️ Build time: 500 min/mês

### PostgreSQL:
- ✅ 1 GB de armazenamento
- ✅ Conexões ilimitadas
- ✅ Não dorme
- ⚠️ Expira após 90 dias (pode renovar)

---

## 📚 Recursos Adicionais

- [Documentação Render](https://render.com/docs)
- [Deploy Spring Boot](https://render.com/docs/deploy-spring-boot)
- [PostgreSQL no Render](https://render.com/docs/databases)
- [Variáveis de Ambiente](https://render.com/docs/environment-variables)

---

## ✅ Checklist de Deploy

- [ ] Conta criada no Render.com
- [ ] PostgreSQL provisionado
- [ ] Credenciais do banco copiadas
- [ ] Web Service criado
- [ ] Repositório conectado
- [ ] Variáveis de ambiente configuradas
- [ ] Build executado com sucesso
- [ ] Aplicação acessível via URL pública
- [ ] Endpoints testados em produção
- [ ] URL documentada no README
- [ ] Badge adicionado no README

---

**Pronto! Sua aplicação está em produção! 🎉**

**URL:** https://fase-1-spring-explorer.onrender.com

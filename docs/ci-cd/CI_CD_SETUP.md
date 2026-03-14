# 🔄 Guia: CI/CD com SonarCloud + Render.com

Este guia configura um pipeline completo de CI/CD:
- ✅ **PR:** SonarCloud valida código e bloqueia se falhar
- ✅ **Merge:** Deploy automático no Render.com após aprovação

---

## 🎯 Fluxo Completo

```
1. Developer cria PR
   ↓
2. GitHub Actions roda SonarCloud
   ↓
3. Quality Gate valida código
   ↓
4a. ❌ Falhou → PR bloqueado (não pode mergear)
4b. ✅ Passou → PR pode ser mergeado
   ↓
5. Merge na branch main
   ↓
6. GitHub Actions triggera deploy no Render
   ↓
7. Render faz build e deploy automático
   ↓
8. ✅ Aplicação em produção atualizada
```

---

## 📋 Pré-requisitos

- [ ] Repositório no GitHub
- [ ] SonarCloud configurado (SONAR_TOKEN)
- [ ] Aplicação deployada no Render.com
- [ ] Render Deploy Hook URL

---

## 🔧 Passo 1: Configurar Branch Protection (GitHub)

### 1.1 Proteger Branch Main

1. No GitHub, vá em **Settings** → **Branches**
2. Clique em **Add branch protection rule**
3. **Branch name pattern:** `main`
4. Marque as opções:
   - ✅ **Require a pull request before merging**
   - ✅ **Require status checks to pass before merging**
   - ✅ **Require branches to be up to date before merging**
5. Em **Status checks**, adicione:
   - `SonarCloud Quality Gate`
6. Clique em **Create**

Agora PRs só podem ser mergeados se o SonarCloud passar! 🎉

---

## 🔑 Passo 2: Obter Render Deploy Hook

### 2.1 Acessar Render Dashboard

1. Acesse [render.com](https://render.com)
2. Clique no seu Web Service: `fase-1-spring-explorer`
3. Vá em **Settings**
4. Role até **Deploy Hook**
5. Copie a URL (formato: `https://api.render.com/deploy/srv-xxxxx?key=yyyyy`)

### 2.2 Adicionar Secret no GitHub

1. No GitHub, vá em **Settings** → **Secrets and variables** → **Actions**
2. Clique em **New repository secret**
3. **Name:** `RENDER_DEPLOY_HOOK_URL`
4. **Value:** Cole a URL copiada
5. Clique em **Add secret**

---

## 📝 Passo 3: Atualizar Workflow (Já Feito!)

O arquivo `.github/workflows/sonarcloud.yml` já foi atualizado com:

### Job 1: SonarCloud Quality Gate
```yaml
sonarcloud:
  - Roda em PRs e push na main
  - Executa análise do SonarCloud
  - Valida Quality Gate
  - Bloqueia PR se falhar
```

### Job 2: Deploy no Render
```yaml
deploy:
  - Depende do job sonarcloud (needs: sonarcloud)
  - Só roda em push na main (após merge)
  - Triggera deploy no Render via webhook
```

---

## 🧪 Passo 4: Testar o Fluxo

### 4.1 Criar Branch e Fazer Alteração

```bash
git checkout -b feature/teste-ci-cd
echo "// teste CI/CD" >> src/main/java/Test.java
git add .
git commit -m "test: valida pipeline CI/CD"
git push origin feature/teste-ci-cd
```

### 4.2 Criar Pull Request

1. No GitHub, clique em **Compare & pull request**
2. Crie o PR
3. Aguarde o workflow executar

### 4.3 Verificar SonarCloud

Você verá:
- ⏳ **Status check:** SonarCloud Quality Gate (em execução)
- ✅ **Passou:** Botão de merge habilitado
- ❌ **Falhou:** Botão de merge bloqueado

### 4.4 Mergear PR (se passou)

1. Clique em **Merge pull request**
2. Confirme o merge
3. Aguarde deploy automático no Render

### 4.5 Verificar Deploy

1. Vá em **Actions** no GitHub
2. Veja o job **Deploy to Render** executando
3. Acesse o Render dashboard
4. Veja os logs do deploy em tempo real
5. Teste a aplicação: `https://fase-1-spring-explorer.onrender.com`

---

## 🎨 Passo 5: Adicionar Badges no README

Adicione no `README.md`:

```markdown
# Sistema de Gerenciamento de Usuários

[![CI/CD Pipeline](https://github.com/SEU_USUARIO/fase-1-spring-explorer/actions/workflows/sonarcloud.yml/badge.svg)](https://github.com/SEU_USUARIO/fase-1-spring-explorer/actions/workflows/sonarcloud.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=SEU_USUARIO_fase-1-spring-explorer&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=SEU_USUARIO_fase-1-spring-explorer)
[![Deploy on Render](https://img.shields.io/badge/Deploy-Render-46E3B7?style=flat&logo=render&logoColor=white)](https://fase-1-spring-explorer.onrender.com)

## 🚀 Deploy

**Produção:** https://fase-1-spring-explorer.onrender.com

### Pipeline CI/CD

- ✅ **Pull Request:** SonarCloud valida código automaticamente
- ✅ **Quality Gate:** Bloqueia merge se houver problemas
- ✅ **Deploy:** Automático após merge na main
```

---

## ⚙️ Configurações Avançadas

### Configurar Quality Gate Customizado

No SonarCloud:
1. **Administration** → **Quality Gates**
2. Crie um novo Quality Gate
3. Defina regras:
   - **Coverage:** ≥ 80%
   - **Bugs:** = 0
   - **Vulnerabilities:** = 0
   - **Code Smells:** ≤ 10
   - **Duplications:** ≤ 3%

### Notificações no Slack (Opcional)

Adicione no workflow:

```yaml
- name: Notify Slack
  if: failure()
  uses: 8398a7/action-slack@v3
  with:
    status: ${{ job.status }}
    webhook_url: ${{ secrets.SLACK_WEBHOOK }}
```

### Deploy Preview no Render (Opcional)

Para cada PR, crie um ambiente temporário:

```yaml
- name: Create Preview Environment
  run: |
    curl -X POST https://api.render.com/v1/services \
      -H "Authorization: Bearer ${{ secrets.RENDER_API_KEY }}" \
      -d '{"type":"web_service","name":"pr-${{ github.event.number }}"}'
```

---

## 🔧 Troubleshooting

### Problema: Quality Gate não aparece no PR

**Solução:**
- Verifique se o SONAR_TOKEN está configurado
- Certifique-se que o workflow rodou completamente
- Aguarde alguns minutos (pode demorar)

### Problema: Deploy não triggera após merge

**Solução:**
- Verifique se RENDER_DEPLOY_HOOK_URL está correto
- Teste o webhook manualmente: `curl -X POST $URL`
- Veja os logs no GitHub Actions

### Problema: PR bloqueado mesmo com código OK

**Solução:**
- Vá no SonarCloud e veja os detalhes
- Corrija os problemas apontados
- Faça novo commit no PR
- Workflow roda automaticamente

### Problema: Deploy falha no Render

**Solução:**
- Veja os logs no Render dashboard
- Verifique se o Dockerfile está correto
- Certifique-se que as variáveis de ambiente estão configuradas

---

## 📊 Monitoramento

### GitHub Actions

- **Actions** → Veja histórico de execuções
- **Insights** → **Actions** → Métricas de CI/CD

### SonarCloud

- Dashboard com métricas de qualidade
- Histórico de análises
- Tendências de código

### Render

- **Logs** → Logs em tempo real
- **Events** → Histórico de deploys
- **Metrics** → CPU, memória, requests

---

## 🎯 Boas Práticas

### 1. Sempre trabalhe em branches
```bash
git checkout -b feature/nova-funcionalidade
```

### 2. Commits pequenos e frequentes
```bash
git commit -m "feat: adiciona validação de email"
```

### 3. Rode testes localmente antes do PR
```bash
mvn clean test
```

### 4. Corrija problemas do SonarCloud imediatamente
- Não ignore warnings
- Mantenha cobertura de testes alta

### 5. Monitore deploys
- Verifique logs após deploy
- Teste endpoints em produção

---

## 📚 Fluxo de Trabalho Recomendado

### Desenvolvimento de Nova Feature

```bash
# 1. Criar branch
git checkout -b feature/nova-funcionalidade

# 2. Desenvolver
# ... código ...

# 3. Testar localmente
mvn clean test

# 4. Commit
git add .
git commit -m "feat: adiciona nova funcionalidade"

# 5. Push
git push origin feature/nova-funcionalidade

# 6. Criar PR no GitHub
# ... aguardar SonarCloud ...

# 7. Se passou, mergear
# ... deploy automático ...

# 8. Verificar produção
curl https://fase-1-spring-explorer.onrender.com/api/usuarios
```

---

## ✅ Checklist de Configuração

- [ ] Branch protection configurada na main
- [ ] Status check do SonarCloud obrigatório
- [ ] RENDER_DEPLOY_HOOK_URL configurado nos secrets
- [ ] Workflow atualizado (.github/workflows/sonarcloud.yml)
- [ ] Testado fluxo completo (PR → SonarCloud → Merge → Deploy)
- [ ] Badges adicionados no README
- [ ] Documentação atualizada

---

## 🎉 Benefícios do CI/CD

✅ **Qualidade garantida** - Código sempre validado antes de produção  
✅ **Deploy automático** - Sem intervenção manual  
✅ **Feedback rápido** - Problemas detectados em minutos  
✅ **Histórico completo** - Rastreabilidade de mudanças  
✅ **Segurança** - Branch protegida, não pode quebrar produção  
✅ **Produtividade** - Foco no código, não em deploy  

---

**Pronto! Agora você tem um pipeline CI/CD profissional! 🚀**

**Fluxo:** PR → SonarCloud → Quality Gate → Merge → Deploy Automático → Produção

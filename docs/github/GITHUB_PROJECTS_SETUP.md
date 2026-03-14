# 🚀 Guia: Configurar GitHub Projects (Kanban Board)

## 📋 Passo 1: Criar o Repositório (se ainda não tiver)

1. Acesse [GitHub](https://github.com)
2. Clique em **"New repository"**
3. Nome: `tech-challenge-fase1` (ou o nome que preferir)
4. Descrição: `Sistema de Gerenciamento de Usuários - Tech Challenge FIAP`
5. Escolha **Public**
6. Marque **"Add a README file"**
7. Clique em **"Create repository"**

---

## 📊 Passo 2: Criar o GitHub Project

1. No seu repositório, clique na aba **"Projects"**
2. Clique em **"Link a project"** → **"New project"**
3. Escolha o template **"Board"** (Kanban)
4. Nome do projeto: `Tech Challenge - Sprint Board`
5. Clique em **"Create"**

---

## 🎯 Passo 3: Configurar as Colunas do Kanban

O GitHub Projects já vem com colunas padrão. Vamos personalizá-las:

### Colunas Recomendadas:

1. **📦 Backlog** (já existe como "Todo")
   - Renomear: Clique nos 3 pontos → "Rename" → "Backlog"

2. **📝 To Do** 
   - Adicionar: Clique em "+ Add column"
   - Nome: "To Do"

3. **⚙️ In Progress** (já existe)
   - Manter como está

4. **✅ Done** (já existe)
   - Manter como está

---

## 🏷️ Passo 4: Criar Labels no Repositório

1. No repositório, vá em **"Issues"** → **"Labels"**
2. Clique em **"New label"** e crie as seguintes:

### Labels de Prioridade:
- `priority: high` - Cor: `#d73a4a` (vermelho)
- `priority: medium` - Cor: `#fbca04` (amarelo)
- `priority: low` - Cor: `#0e8a16` (verde)

### Labels de Épico:
- `épico: configuração` - Cor: `#1d76db`
- `épico: entidade-dados` - Cor: `#5319e7`
- `épico: service` - Cor: `#0052cc`
- `épico: api` - Cor: `#006b75`
- `épico: erros` - Cor: `#c5def5`
- `épico: segurança` - Cor: `#d93f0b`
- `épico: docker` - Cor: `#0366d6`
- `épico: testes` - Cor: `#bfdadc`
- `épico: documentação` - Cor: `#c2e0c6`
- `épico: finalização` - Cor: `#e99695`

### Labels de Estimativa:
- `pontos: 1` - Cor: `#ededed`
- `pontos: 2` - Cor: `#ededed`
- `pontos: 3` - Cor: `#ededed`
- `pontos: 5` - Cor: `#ededed`

---

## 📝 Passo 5: Criar Milestones (Sprints)

1. Vá em **"Issues"** → **"Milestones"**
2. Clique em **"New milestone"**
3. Crie 4 milestones:

### Sprint 1 - Fundação
- **Título:** Sprint 1 - Fundação
- **Data:** Defina início e fim (ex: 2 semanas)
- **Descrição:** Configuração inicial, entidade, repository e service (Tasks 1-7)
- **Épicos incluídos:** Configuração Inicial, Entidade e Dados, Camada de Serviço

### Sprint 2 - API e Segurança
- **Título:** Sprint 2 - API e Segurança
- **Data:** Próximas 2 semanas
- **Descrição:** Endpoints REST, tratamento de erros e segurança (Tasks 8-17)
- **Épicos incluídos:** API RESTful, Tratamento de Erros, Segurança

### Sprint 3 - Docker e Testes
- **Título:** Sprint 3 - Docker e Testes
- **Data:** Próximas 2 semanas
- **Descrição:** Containerização e collections de teste (Tasks 18-22)
- **Épicos incluídos:** Docker, Testes

### Sprint 4 - Documentação e Entrega
- **Título:** Sprint 4 - Documentação e Entrega
- **Data:** Últimas 2 semanas
- **Descrição:** Documentação completa, deploy no Render.com e preparação para entrega (Tasks 23-31)
- **Épicos incluídos:** Documentação, Finalização

---

## 📋 Passo 6: Criar as Issues

Agora vamos criar as 30 issues. Use os templates que preparei no arquivo `GITHUB_ISSUES_TEMPLATES.md`

### Como criar cada issue:

1. Vá em **"Issues"** → **"New issue"**
2. Copie o template correspondente
3. Cole no campo de descrição
4. Adicione as **Labels** apropriadas
5. Selecione o **Milestone** (Sprint)
6. Clique em **"Submit new issue"**
7. No lado direito, em **"Projects"**, adicione ao seu projeto
8. A issue aparecerá automaticamente na coluna **"Backlog"**

---

## 🎨 Passo 7: Organizar o Board

1. Acesse seu **Project Board**
2. Arraste as issues entre as colunas conforme o status:
   - **Backlog:** Todas as tasks que ainda não foram iniciadas
   - **To Do:** Tasks priorizadas para trabalhar em breve
   - **In Progress:** Tasks que você está trabalhando agora
   - **Done:** Tasks concluídas

### Dica: Comece movendo para "To Do" as tasks do Sprint 1:
- TASK-001 a TASK-007

---

## 🔄 Passo 8: Workflow Diário

### Ao começar o dia:
1. Abra o Project Board
2. Mova uma task de "To Do" para "In Progress"
3. Trabalhe na task

### Ao concluir uma task:
1. Faça commit do código
2. Mova a issue para "Done"
3. Feche a issue (opcional)

### Ao finalizar um Sprint:
1. Revise todas as tasks "Done"
2. Mova tasks não concluídas para o próximo Sprint
3. Planeje o próximo Sprint

---

## 📊 Passo 9: Visualizações Úteis

### View 1: Por Prioridade
1. No Project, clique em "View 1"
2. Clique em "Group by" → "Labels"
3. Filtre por labels de prioridade

### View 2: Por Sprint
1. Crie nova view
2. "Group by" → "Milestone"
3. Veja todas as tasks organizadas por Sprint

### View 3: Por Épico
1. Crie nova view
2. "Group by" → "Labels"
3. Filtre por labels de épico

---

## 🎯 Dicas Extras

### Automatizações:
- Configure para mover automaticamente issues fechadas para "Done"
- Settings do Project → Workflows → "Item closed"

### Filtros Úteis:
- `is:open label:"priority: high"` - Issues abertas de alta prioridade
- `milestone:"Sprint 1"` - Todas as issues do Sprint 1
- `assignee:@me` - Issues atribuídas a você

### Integração com Commits:
- Nos commits, use: `git commit -m "feat: implementa login #13"`
- O `#13` cria link automático com a issue

---

## ✅ Checklist de Configuração

- [ ] Repositório criado
- [ ] GitHub Project criado
- [ ] Colunas do Kanban configuradas
- [ ] Labels criadas
- [ ] Milestones (Sprints) criados
- [ ] 30 Issues criadas com templates
- [ ] Issues adicionadas ao Project
- [ ] Issues organizadas no Backlog
- [ ] Primeiras tasks movidas para "To Do"

---

## 🆘 Problemas Comuns

**Issue não aparece no Project:**
- Certifique-se de adicionar a issue ao projeto (lado direito da issue)

**Não consigo arrastar issues:**
- Verifique se está na view "Board" e não "Table"

**Labels não aparecem:**
- Crie as labels no repositório primeiro, depois adicione nas issues

---

## 📚 Recursos Adicionais

- [Documentação GitHub Projects](https://docs.github.com/en/issues/planning-and-tracking-with-projects)
- [GitHub Projects Beta](https://github.com/features/issues)
- [Guia de Markdown](https://guides.github.com/features/mastering-markdown/)

---

**Pronto! Agora você tem um board Kanban profissional para gerenciar seu Tech Challenge! 🎉**

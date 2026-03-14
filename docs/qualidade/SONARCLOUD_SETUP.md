# 🔍 Guia: Configurar SonarCloud com GitHub Actions

Este guia mostra como configurar análise automática de código com SonarCloud em Pull Requests.

---

## 🎯 O que o SonarCloud faz?

✅ Detecta bugs e vulnerabilidades  
✅ Identifica code smells  
✅ Mede cobertura de testes  
✅ Analisa duplicação de código  
✅ Verifica segurança (OWASP Top 10)  
✅ Comenta automaticamente nos PRs  

---

## 📋 Passo 1: Criar Conta no SonarCloud

1. Acesse [sonarcloud.io](https://sonarcloud.io)
2. Clique em **"Sign up"**
3. Escolha **"With GitHub"**
4. Autorize o SonarCloud a acessar seus repositórios
5. Pronto! ✅

---

## 🔗 Passo 2: Importar Repositório

1. No SonarCloud, clique em **"+"** → **"Analyze new project"**
2. Selecione a organização do GitHub
3. Escolha o repositório: `fase-1-spring-explorer`
4. Clique em **"Set Up"**
5. Escolha **"With GitHub Actions"**

---

## 🔑 Passo 3: Configurar Token no GitHub

1. No SonarCloud, copie o **SONAR_TOKEN** gerado
2. Vá no seu repositório GitHub
3. **Settings** → **Secrets and variables** → **Actions**
4. Clique em **"New repository secret"**
5. Nome: `SONAR_TOKEN`
6. Value: Cole o token copiado
7. Clique em **"Add secret"**

---

## ⚙️ Passo 4: Atualizar Configurações

### 4.1 Atualizar sonar-project.properties

Abra o arquivo `sonar-project.properties` e substitua:

```properties
sonar.projectKey=SEU_USUARIO_fase-1-spring-explorer
sonar.organization=SEU_USUARIO
```

Por:

```properties
sonar.projectKey=seu-usuario-github_fase-1-spring-explorer
sonar.organization=seu-usuario-github
```

**Exemplo:**
```properties
sonar.projectKey=jefferson_fase-1-spring-explorer
sonar.organization=jefferson
```

### 4.2 Atualizar GitHub Actions Workflow

Abra `.github/workflows/sonarcloud.yml` e atualize a linha:

```yaml
-Dsonar.projectKey=SEU_USUARIO_fase-1-spring-explorer
```

Para:

```yaml
-Dsonar.projectKey=seu-usuario-github_fase-1-spring-explorer
```

---

## 📦 Passo 5: Adicionar Plugin no pom.xml

Adicione o plugin JaCoCo no seu `pom.xml` para cobertura de testes:

```xml
<build>
    <plugins>
        <!-- Plugin JaCoCo para cobertura de código -->
        <plugin>
            <groupId>org.jacoco</groupId>
            <artifactId>jacoco-maven-plugin</artifactId>
            <version>0.8.11</version>
            <executions>
                <execution>
                    <goals>
                        <goal>prepare-agent</goal>
                    </goals>
                </execution>
                <execution>
                    <id>report</id>
                    <phase>test</phase>
                    <goals>
                        <goal>report</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

---

## 🚀 Passo 6: Testar a Configuração

### 6.1 Commit e Push

```bash
git add .
git commit -m "ci: adiciona SonarCloud analysis"
git push origin main
```

### 6.2 Verificar Execução

1. Vá em **Actions** no GitHub
2. Veja o workflow **"SonarCloud Analysis"** rodando
3. Aguarde finalizar (2-5 minutos)

### 6.3 Ver Resultados

1. Acesse [sonarcloud.io](https://sonarcloud.io)
2. Clique no seu projeto
3. Veja o dashboard com métricas

---

## 🔄 Passo 7: Testar em Pull Request

### 7.1 Criar Branch e PR

```bash
git checkout -b feature/teste-sonar
echo "// teste" >> src/main/java/Test.java
git add .
git commit -m "test: adiciona arquivo de teste"
git push origin feature/teste-sonar
```

### 7.2 Criar Pull Request

1. Vá no GitHub
2. Clique em **"Compare & pull request"**
3. Crie o PR
4. Aguarde o SonarCloud analisar
5. Veja os comentários automáticos no PR! 🎉

---

## 📊 O que Aparece no PR?

O SonarCloud adiciona:

✅ **Status Check** - Pass/Fail  
✅ **Comentários** - Bugs e vulnerabilidades encontrados  
✅ **Quality Gate** - Aprovado ou reprovado  
✅ **Métricas** - Cobertura, duplicação, etc  
✅ **Link** - Para ver detalhes completos  

---

## 🎨 Adicionar Badge no README

Adicione no seu `README.md`:

```markdown
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=SEU_USUARIO_fase-1-spring-explorer&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=SEU_USUARIO_fase-1-spring-explorer)

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=SEU_USUARIO_fase-1-spring-explorer&metric=bugs)](https://sonarcloud.io/summary/new_code?id=SEU_USUARIO_fase-1-spring-explorer)

[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=SEU_USUARIO_fase-1-spring-explorer&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=SEU_USUARIO_fase-1-spring-explorer)

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=SEU_USUARIO_fase-1-spring-explorer&metric=coverage)](https://sonarcloud.io/summary/new_code?id=SEU_USUARIO_fase-1-spring-explorer)
```

---

## ⚙️ Configurações Avançadas (Opcional)

### Quality Gate Personalizado

No SonarCloud:
1. **Administration** → **Quality Gates**
2. Crie um novo Quality Gate
3. Defina regras customizadas:
   - Cobertura mínima: 80%
   - Bugs: 0
   - Vulnerabilidades: 0
   - Code Smells: < 10

### Excluir Arquivos da Análise

Edite `sonar-project.properties`:

```properties
sonar.exclusions=**/config/**,**/dto/**,**/entity/**,**/*Application.java,**/test/**
```

---

## 🔧 Troubleshooting

### Erro: "SONAR_TOKEN not found"
- Verifique se adicionou o secret no GitHub
- Nome deve ser exatamente `SONAR_TOKEN`

### Erro: "Project key not found"
- Verifique se o projectKey está correto
- Deve ser igual ao configurado no SonarCloud

### Workflow não executa
- Verifique se o arquivo está em `.github/workflows/`
- Verifique a sintaxe YAML

### Análise muito lenta
- Normal na primeira execução
- Cache será usado nas próximas

---

## 📚 Recursos Adicionais

- [Documentação SonarCloud](https://docs.sonarcloud.io/)
- [SonarCloud GitHub Action](https://github.com/SonarSource/sonarcloud-github-action)
- [Regras Java](https://rules.sonarsource.com/java/)

---

## ✅ Checklist de Configuração

- [ ] Conta criada no SonarCloud
- [ ] Repositório importado no SonarCloud
- [ ] SONAR_TOKEN adicionado nos secrets do GitHub
- [ ] sonar-project.properties atualizado
- [ ] Workflow sonarcloud.yml atualizado
- [ ] Plugin JaCoCo adicionado no pom.xml
- [ ] Commit e push realizados
- [ ] Workflow executado com sucesso
- [ ] PR testado com análise automática
- [ ] Badge adicionado no README

---

**Pronto! Agora todo PR terá análise automática de qualidade de código! 🎉**

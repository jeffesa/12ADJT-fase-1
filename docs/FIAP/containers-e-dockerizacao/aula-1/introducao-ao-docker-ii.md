# 🐳 Executando o Primeiro Container Docker — Resumo para Estudo

## 1. ⚙️ Pré-requisitos para executar containers

Antes de executar qualquer container é necessário:

- abrir o **Docker Desktop** primeiro
- depois abrir o **WSL/Ubuntu**

O Docker Desktop precisa estar rodando porque:

- o WSL depende dele para funcionar
- sem o Docker Desktop iniciado o módulo Docker não funciona

---

## 2. ✅ Verificando a instalação do Docker

Para verificar se o Docker está instalado:

```
docker
```

Este comando mostra todos os comandos disponíveis.

Para verificar a versão:

```
docker version
```

Mostra informações sobre:

- versão do Docker Desktop
- versão do Docker Engine (Community/gratuito)

---

## 3. 🚀 Executando o primeiro container

### Comando básico

```
docker container run hello-world
```

O que acontece:

1. Docker procura a imagem localmente
2. Se não encontrar faz o **download do Docker Hub**
3. Executa o container
4. Mostra uma mensagem de saída
5. Container **morre automaticamente**

### Por que o container morre?

Containers executam uma tarefa e depois param.

Se a única função é mostrar uma mensagem:

- mostra a mensagem
- encerra automaticamente

---

## 4. 🔄 Diferença entre pull e run

### 📥 Docker pull

```
docker pull hello-world
```

- apenas **baixa a imagem**
- não executa o container

### ▶️ Docker run

```
docker container run hello-world
```

- baixa a imagem (se necessário)
- **executa o container**

---

## 5. 📋 Listando containers

### Containers em execução

```
docker container ls
```

Mostra apenas containers **rodando no momento**.

### Todos os containers (incluindo parados)

```
docker container ls -a
```

Mostra containers com status:

- **Up**: rodando
- **Exited**: parado/morto

---

## 6. 💚 Exemplo com imagem Node

```
docker container run node:18-slim
```

O que acontece:

- baixa a imagem `node:18-slim`
- executa o container
- container morre imediatamente

### Tags de imagem

A nomenclatura após os dois pontos indica:

- **versão** da imagem
- **tamanho** da imagem
- **o que está instalado** dentro dela

Exemplo: `node:18-slim`

- versão 18 do Node
- versão slim (menor tamanho)

---

## 7. 🛠️ Principais comandos Docker

### ▶️ run
Cria e roda um novo container a partir de uma imagem.

### ⚡ exec
Executa um comando em um container que já está rodando.

### 📋 ps ou ls
Lista os containers.

### 🔨 build
Cria uma imagem a partir de um Dockerfile.

### 📥 pull
Faz download da imagem (sem executar).

### 📤 push
Envia a imagem para um repositório.

### 🖼️ image
Lista as imagens.

### 🔐 login
Loga no Docker Hub.

### 🚪 logout
Desloga do Docker Hub.

### 🔍 search
Pesquisa imagens no Docker Hub.

### ℹ️ info
Traz informações do sistema.

---

## 8. 🖼️ Listando imagens baixadas

```
docker image ls
```

Mostra todas as imagens locais com:

- nome da imagem
- tag
- tamanho

Exemplo:

- `node:18-slim` → 196 MB
- `hello-world` → 13 KB

---

## 9. 🖥️ Usando o Docker Desktop

No Docker Desktop é possível:

- visualizar **containers criados**
- visualizar **imagens baixadas**
- ver **uso de armazenamento**
- **iniciar/parar** containers clicando no botão play/stop
- **excluir** containers

---

## 10. 💬 Modo interativo (-it)

```
docker container run -it node:18-slim
```

A flag `-it` significa:

- **interativo**
- permite **entrar dentro do container**
- não apenas rodar e sair

Para sair do modo interativo:

```
Ctrl + C
```

Isso **força o container a morrer**.

---

## 11. ⏹️ Parando containers

### Pelo WSL

```
docker container kill <container-id>
```

### Pelo Docker Desktop

- clicar em **stop**
- ou **excluir** o container

---

## 12. 📚 Consultando ajuda

```
docker
```

ou

```
docker --help
```

Mostra:

- todos os comandos disponíveis
- flags possíveis
- documentação da engine do Docker

---

## 💡 Ideia principal da aula

Para executar containers Docker:

1. iniciar o Docker Desktop
2. abrir o WSL/Ubuntu
3. usar `docker container run` para executar
4. containers morrem após completar sua tarefa
5. usar `-it` para modo interativo
6. usar `docker container ls` para listar containers
7. usar `docker image ls` para listar imagens

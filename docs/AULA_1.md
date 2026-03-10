# Docker e Containers — Resumo para Estudo

## 1. Problema no desenvolvimento tradicional
Um dos principais problemas no desenvolvimento de software é o uso excessivo de recursos da máquina, como:

- CPU
- Memória RAM
- Disco
- Rede

Quando precisamos rodar **várias aplicações na mesma máquina**, podem ocorrer conflitos de dependências.

Exemplo:  
Uma aplicação pode precisar de **Node.js** e outra de **Python**, cada uma com bibliotecas e versões diferentes.

Isso gera problemas como:

- dependências misturadas no sistema
- aplicações interferindo entre si
- maior consumo de recursos da máquina

---

## 2. Uso de máquinas virtuais (VM)

Antes dos containers, uma solução comum era utilizar **máquinas virtuais**.

Uma máquina virtual cria **um sistema operacional completo dentro de outro sistema**.

Ou seja, cada aplicação roda dentro de um sistema operacional separado.

Problemas das VMs:

- consomem muito **espaço em disco**
- utilizam muita **memória e CPU**
- precisam instalar **todas as dependências novamente**
- inicialização mais lenta

Isso torna o ambiente pesado e pouco eficiente quando precisamos rodar várias aplicações.

---

## 3. Containers

Os **containers** surgiram para resolver esses problemas.

Um container é um **ambiente isolado que executa uma aplicação junto com suas dependências**, mas sem precisar de um sistema operacional completo.

Características:

- cada aplicação roda em **seu próprio container**
- containers são **isolados entre si**
- compartilham o **mesmo sistema operacional da máquina**

### Analogia apresentada na aula

O professor compara containers com um **navio cargueiro**:

- o **navio** representa a máquina
- cada **container de carga** representa um container de software

Se um container apresentar problema:

- os outros **não são afetados**
- o sistema continua funcionando normalmente.

---

## 4. Docker

O **Docker** é a ferramenta responsável por **gerenciar containers**.

Ele funciona como um **motor (engine)** que controla:

- criação de containers
- execução de containers
- gerenciamento de imagens
- configuração de ambientes

Com Docker podemos criar ambientes **padronizados e reproduzíveis** para executar aplicações.

---

## 5. Base do Docker no Linux

Docker utiliza recursos do **sistema Linux** para funcionar.

### Kernel

O **Kernel** é o núcleo do sistema operacional.

Ele faz a comunicação entre:

- hardware
- software

Docker usa o kernel do Linux para gerenciar processos e recursos.

---

### Cgroups (Control Groups)

Os **Cgroups** são responsáveis por **controlar o uso de recursos**.

Eles limitam:

- CPU
- memória
- rede

Assim cada container usa apenas os recursos definidos.

---

### Namespaces

Os **Namespaces** fazem o **isolamento dos processos**.

Cada container possui:

- usuários próprios
- processos próprios
- rede própria
- sistema de arquivos isolado

Exemplo da aula:

Se um usuário for criado dentro de um container:

- ele **não existe nos outros containers**.

---

## 6. Docker no Windows

Docker foi criado para **Linux**.

Para rodar Docker no Windows utilizamos o **WSL (Windows Subsystem for Linux)**.

O WSL permite executar **um ambiente Linux dentro do Windows**.

Vantagens:

- não precisa instalar Linux em outro computador
- não precisa formatar o sistema
- permite usar ferramentas Linux diretamente no Windows.

---

## 7. Instalação do WSL

A instalação pode ser feita pelo **PowerShell como administrador**.

Comando:

```
wsl --install
```

Esse comando instala automaticamente:

- kernel Linux
- máquina virtual leve
- distribuição Linux padrão (Ubuntu)

---

## 8. Distribuições Linux disponíveis

Exemplos disponíveis no WSL:

- Ubuntu
- Debian
- Kali Linux

Para listar distribuições disponíveis:

```
wsl --list --online
```

Para instalar uma distribuição específica:

```
wsl --install -d Debian
```

---

## 9. Versões do WSL

### WSL 1

- menor nível de isolamento
- menos eficiente

### WSL 2

- melhor kernel Linux
- melhor performance
- maior isolamento

Docker **funciona apenas com WSL 2**.

---

## 10. Instalação do Docker

Passos básicos:

1. baixar **Docker Desktop**
2. instalar no Windows
3. abrir as configurações do Docker
4. ativar **integração com WSL**

Depois disso Docker passa a usar o ambiente Linux para **executar e gerenciar containers**.

---

## Ideia principal da aula

Docker foi criado para:

- reduzir consumo de recursos
- evitar conflitos de dependências
- permitir rodar várias aplicações na mesma máquina
- garantir isolamento entre aplicações

Tudo isso através de **containers leves e isolados gerenciados pelo Docker**.
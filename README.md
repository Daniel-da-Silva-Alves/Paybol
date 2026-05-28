<div align="center">
  <img src="src/main/webapp/resources/images/paybol.PNG" alt="Paybol Logo" width="180">

  # Paybol

  Sistema web para gerenciar a lista de jogadores da pelada, com controle de pagamento<br>e dashboard de arrecadação — tudo persistido em banco de dados MySQL.

  [![Java](https://img.shields.io/badge/Java-17-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://adoptium.net/)
  [![Jakarta EE](https://img.shields.io/badge/Jakarta_EE-10-E76F00?style=flat-square&logo=jakartaee&logoColor=white)](https://jakarta.ee/)
  [![JSF](https://img.shields.io/badge/JSF-4.0-2196F3?style=flat-square)](https://jakarta.ee/specifications/faces/)
  [![PrimeFaces](https://img.shields.io/badge/PrimeFaces-14-3F51B5?style=flat-square&logo=primefaces&logoColor=white)](https://www.primefaces.org/)
  [![Hibernate](https://img.shields.io/badge/Hibernate-6.4-59666C?style=flat-square&logo=hibernate&logoColor=white)](https://hibernate.org/)
  [![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=flat-square&logo=mysql&logoColor=white)](https://www.mysql.com/)
  [![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=flat-square&logo=apachemaven&logoColor=white)](https://maven.apache.org/)
  [![Tomcat](https://img.shields.io/badge/Tomcat-10.1-F8DC75?style=flat-square&logo=apachetomcat&logoColor=black)](https://tomcat.apache.org/)

</div>

---

## Visão Geral

O **Paybol** é uma aplicação web CRUD desenvolvida com **Jakarta EE** para substituir as listas desorganizadas de grupos de WhatsApp por uma interface web limpa e funcional. Ele permite cadastrar jogadores, controlar quem já pagou e acompanhar o valor arrecadado em tempo real.

A aplicação possui três pilares:

1. **Lista de Jogadores** — Tabela interativa com cadastro, edição, exclusão e ordenação por nome, valor e status de pagamento.

2. **Controle de Pagamento** — Status visual (PAGO / PENDENTE) alternável com um clique, usando badges coloridos para identificação rápida.

3. **Dashboard de Resumo** — Cards em tempo real com total de jogadores, pagos, pendentes e valor arrecadado (R$).

---

## Funcionalidades

| Funcionalidade | Descrição |
|---|---|
| **Cadastrar jogador** | Nome, telefone/WhatsApp, valor e status de pagamento |
| **Listar jogadores** | Tabela PrimeFaces com ordenação por colunas |
| **Editar jogador** | Atualizar qualquer campo de um jogador existente |
| **Excluir jogador** | Remoção com confirmação de segurança |
| **Alternar pagamento** | Clique no badge para trocar PAGO ↔ PENDENTE |
| **Dashboard** | Total de jogadores, pagos, pendentes e R$ arrecadado |

---

## Tech Stack

| Camada | Tecnologia | Função |
|---|---|---|
| **Plataforma** | Jakarta EE 10 | Base da aplicação web empresarial |
| **Frontend** | JSF 4.0 + PrimeFaces 14 | Interface web com componentes ricos |
| **Controller** | CDI (Weld 5.1) | Injeção de dependência e managed beans |
| **ORM** | Hibernate 6.4 | Mapeamento objeto-relacional |
| **Persistência** | JPA 3.1 | API padrão de acesso a dados |
| **Banco de dados** | MySQL 8.0 | Armazenamento relacional |
| **Servidor** | Apache Tomcat 10.1 | Container de servlets Jakarta |
| **Build** | Apache Maven 3.9 | Gerenciamento de dependências |
| **Runtime** | JDK 17 (Temurin) | Ambiente de execução Java |

---

## Estrutura do Projeto

```
├── pom.xml
├── docs/
│   ├── trabalho-escrito.md
│   └── funcionalidades.md
└── src/main/
    ├── java/com/paybol/
    │   ├── model/
    │   │   ├── Jogador.java
    │   │   └── StatusPagamento.java
    │   ├── dao/
    │   │   └── JogadorDAO.java
    │   ├── bean/
    │   │   └── JogadorBean.java
    │   └── util/
    │       └── JPAUtil.java
    ├── resources/META-INF/
    │   └── persistence.xml
    └── webapp/
        ├── index.xhtml
        ├── cadastrar.xhtml
        ├── editar.xhtml
        ├── resources/
        │   ├── css/style.css
        │   └── images/paybol.PNG
        └── WEB-INF/
            ├── web.xml
            ├── beans.xml
            └── faces-config.xml
```

---

## Pré-requisitos

- [JDK 17](https://adoptium.net/) (Eclipse Temurin)
- [Apache Maven 3.9+](https://maven.apache.org/download.cgi)
- [MySQL Server 8.x](https://dev.mysql.com/downloads/installer/)
- [Apache Tomcat 10.1](https://tomcat.apache.org/download-10.cgi)

---

## Getting Started

### 1. Criar o banco de dados

```sql
CREATE DATABASE paybol_db;
```

### 2. Configurar conexão

Edite `src/main/resources/META-INF/persistence.xml` se necessário:

```xml
<property name="jakarta.persistence.jdbc.url"
          value="jdbc:mysql://localhost:3306/paybol_db"/>
<property name="jakarta.persistence.jdbc.user" value="root"/>
<property name="jakarta.persistence.jdbc.password" value="root"/>
```

### 3. Build e Deploy

```bash
mvn clean package
copy target\paybol.war %CATALINA_HOME%\webapps\
%CATALINA_HOME%\bin\startup.bat
```

### 4. Acessar

```
http://localhost:8080/paybol/
```

> [!NOTE]
> O Hibernate cria a tabela `jogadores` automaticamente na primeira execução.

---

## Arquitetura

```
┌──────────┐    ┌──────────┐    ┌───────────────┐    ┌───────────┐    ┌─────┐    ┌───────────┐    ┌───────┐
│ Browser  │ →  │ Tomcat   │ →  │ JSF/PrimeFaces│ →  │ CDI Bean  │ →  │ DAO │ →  │ Hibernate │ →  │ MySQL │
│  (View)  │    │ (Server) │    │  (Facelets)   │    │(Controller│    │(CRUD│    │   (ORM)   │    │ (DB)  │
└──────────┘    └──────────┘    └───────────────┘    └───────────┘    └─────┘    └───────────┘    └───────┘
```

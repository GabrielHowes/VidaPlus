# 🏥 VidaPlus - Como Executar o Projeto

Este projeto é composto por:
- Back-end em Spring Boot
- Front-end em Angular
- PostgreSQL
- Redis
- Keycloak

---

# ✅ Pré-requisitos

Antes de iniciar, instale:

- Java 21
- Node.js 18+
- Docker
- Docker Compose

---

# 🚀 1. Subir a infraestrutura

Na raiz do projeto, execute:

```bash
docker-compose up -d
```

Esse comando iniciará:
- PostgreSQL
- Redis
- Keycloak
- pgAdmin

---

# ▶️ 2. Executar o Back-end

Entre na pasta do back-end:

```bash
cd backend
```

Execute a aplicação:

```bash
./gradlew bootRun
```

A API ficará disponível em:

```text
http://localhost:8080
```

Swagger:

```text
http://localhost:8080/swagger-ui.html
```

---

# ▶️ 3. Executar o Front-end

Abra outro terminal e entre na pasta do front-end:

```bash
cd frontend
```

Instale as dependências:

```bash
npm install
```

Execute a aplicação:

```bash
npm run dev
```

O front ficará disponível em:

```text
http://localhost:4200
```

---

Credenciais:

| Usuário | Senha |
|---|---|
| admin | admin |

---

# 🗄 pgAdmin

Acesse:

```text
http://localhost:5050
```

Credenciais:

| Usuário | Senha |
|---|---|
| admin@email.com | admin |

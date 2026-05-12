# VidaPlus - Sistema de Gestão Hospitalar e Agendamentos

O **VidaPlus** é uma plataforma robusta de gestão hospitalar projetada para gerenciar pacientes, médicos e agendamentos de consultas. O projeto utiliza uma arquitetura moderna e tecnologias de ponta para garantir escalabilidade, segurança e manutenibilidade.

---

## 🛠 Tecnologias Utilizadas

- **Linguagem:** Java 21
- **Framework:** Spring Boot 3.5.0
- **Persistência:** Spring Data JPA / PostgreSQL
- **Cache:** Redis
- **Segurança:** Spring Security / OAuth2 Resource Server (Keycloak)
- **Documentação:** SpringDoc OpenAPI (Swagger)
- **Mapeamento:** MapStruct
- **Utilitários:** Lombok
- **Infraestrutura:** Docker / Docker Compose

---

## 🏗 Arquitetura do Projeto

O projeto segue os princípios da **Arquitetura Hexagonal (Ports and Adapters)**, dividida em módulos independentes para separar as preocupações de negócio, infraestrutura e interface.

### Estrutura de Módulos:

1.  **`domain-*` (Core/Domínio):**
    *   Contém a lógica de negócio pura, entidades de domínio (Records/DTOs) e as **Ports** (interfaces).
    *   **ApiPort:** Interfaces de entrada que definem o que o sistema faz.
    *   **SpiPort:** Interfaces de saída que definem o que o sistema precisa (ex: persistência).
2.  **`infrastructure-*` (Adapters de Saída):**
    *   Implementa as `SpiPorts`. Contém entidades do banco de dados (JPA), repositórios e integração com serviços externos (Redis, Keycloak).
    *   **Handlers:** Gerenciam a interação direta com os repositórios Spring Data.
3.  **`controller-*` (Adapters de Entrada):**
    *   Expõe a API REST. Transforma as requisições HTTP em chamadas para o domínio.
    *   Utiliza mappers para converter entre `Request/Response` e `Domain DTOs`.
4.  **`common`:**
    *   Funcionalidades compartilhadas entre todos os módulos.

---

## 🚀 Como Executar o Projeto

### Pré-requisitos:
- JDK 21
- Docker e Docker Compose

### Passos:

1.  **Subir a infraestrutura (Banco, Redis e Keycloak):**
    ```bash
    docker-compose up -d
    ```
    *Isso iniciará o PostgreSQL (5432), Redis (6379), Keycloak (8081) e pgAdmin (5050).*

2.  **Compilar o projeto:**
    ```bash
    ./gradlew build
    ```

3.  **Executar a aplicação:**
    ```bash
    ./gradlew bootRun
    ```

---

## 📑 Implementações de Destaque

### Fluxo de Agendamento Detalhado
Recentemente implementamos um endpoint especializado para listagem de consultas que realiza a agregação de dados entre diferentes domínios (Pacientes e Médicos) em nível de aplicação, garantindo que o frontend receba informações ricas sem múltiplas chamadas:

- **Endpoint:** `GET /v1/appointment/detailed-list`
- **Informações retornadas:**
    - ID do Agendamento
    - Nome Completo do Paciente
    - Data e Hora da Consulta
    - Nome do Médico Responsável
    - Status (Agendado/Cancelado)
    - Observações (Notes)

### Segurança com Keycloak
O projeto está integrado ao **Keycloak** para autenticação e autorização via JWT. O realm é importado automaticamente via Docker, facilitando o desenvolvimento local.

---

## 🔗 Links Úteis
- **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **pgAdmin:** [http://localhost:5050](http://localhost:5050) (Admin: `admin@email.com` / `admin`)
- **Keycloak Console:** [http://localhost:8081](http://localhost:8081) (Admin: `admin` / `admin`)

---
© 2026 VidaPlus Team.

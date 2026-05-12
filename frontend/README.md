# VidaPlus - Front-end

Este é o repositório do front-end da plataforma **VidaPlus**, um sistema de gerenciamento de consultas médicas e chat em tempo real, desenvolvido com as tecnologias mais recentes do ecossistema **Angular**.

## 🚀 Tecnologias Utilizadas

O projeto utiliza uma stack moderna e performática:

*   **[Angular 21](https://angular.dev/):** Versão mais recente do framework, utilizando componentes standalone e **Signals** para reatividade.
*   **[Tailwind CSS 4](https://tailwindcss.com/):** Nova geração do framework de estilização, com suporte nativo a PostCSS e alta performance.
*   **[NgRx Signals Store](https://ngrx.io/guide/signals):** Gerenciamento de estado leve e moderno, totalmente integrado aos Signals do Angular.
*   **[Angular Material 21](https://material.angular.io/):** Biblioteca de componentes de UI de alta qualidade seguindo os padrões do Material Design.
*   **[STOMP.js & SockJS](https://stomp-js.github.io/):** Protocolos para comunicação via WebSockets em tempo real (utilizado no Chat).
*   **[Angular OAuth2 OIDC](https://github.com/manfredsteyer/angular-oauth2-oidc):** Implementação robusta de autenticação e autorização via tokens.
*   **[Vitest](https://vitest.dev/):** Runner de testes rápido e moderno para garantir a qualidade do código.

## 📦 Estrutura do Projeto

O código está organizado seguindo as melhores práticas do Angular:

```text
src/app/
├── components/     # Componentes de UI (Dialogs, Listas, Home, Login/Register)
├── service/        # Serviços para comunicação com a API (HTTP)
├── store/          # Gerenciamento de estado global com NgRx Signals
├── guard/          # Guardas de rota para proteção de acesso
├── interceptor/    # Interceptadores HTTP para anexar tokens de autenticação
└── app.routes.ts   # Definição das rotas da aplicação
```

## ✨ Funcionalidades Principais

*   **Autenticação Segura:** Login e Cadastro de usuários integrados via OAuth2.
*   **Gestão de Consultas:** Listagem, criação, edição e exclusão de agendamentos médicos.
*   **Dashboard:** Visualização de consultas mensais e estatísticas.
*   **Chat em Tempo Real:** Comunicação instantânea entre usuários via WebSockets.
*   **Interface Responsiva:** Estilização moderna com Tailwind CSS, adaptável a diversos tamanhos de tela.

## 🛠️ Como Rodar o Projeto

### Pré-requisitos

*   **Node.js:** Versão 18 ou superior.
*   **NPM:** Versão 10 ou superior.

### Passos para Instalação

1.  Clone o repositório:
    ```bash
    git clone <url-do-repositorio>
    cd vidaplus_front
    ```

2.  Instale as dependências:
    ```bash
    npm install
    ```

3.  Configure o backend:
    O projeto está configurado para apontar para um backend rodando em `http://localhost:9000/v1` (via `proxy.conf.json`). Certifique-se de que a API esteja ativa ou altere o arquivo de proxy se necessário.

4.  Inicie o servidor de desenvolvimento:
    ```bash
    npm start
    ```

5.  Acesse a aplicação:
    Abra o seu navegador em [http://localhost:4200](http://localhost:4200).

## 🧪 Testes e Build

*   **Executar Testes:** `npm test`
*   **Gerar Build de Produção:** `npm run build` (os arquivos serão gerados na pasta `dist/`)

---

Desenvolvido com ❤️ pela equipe VidaPlus.

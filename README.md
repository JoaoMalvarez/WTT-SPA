# Star Wars Cards — SPA (MackLeaps)

Catálogo interativo de personagens de Star Wars, construído em **Vue 3 + TypeScript** como exercício prático do **Módulo 1 — SPA** da trilha de workshops do **MackLeaps** (Laboratório de Estudos de Ambientes de Produção de Software, FCI/Universidade Presbiteriana Mackenzie).

O objetivo é aprender, na prática, como funciona uma *Single Page Application*: navegação sem recarregar a página, componentização, consumo de API e organização de rotas — tudo isso com uma ambientação temática de Star Wars.

## Trilha do workshop

Este projeto é o primeiro de três módulos:

1. **SPA** *(este repositório)* — front-end em Vue: roteamento, componentes, consumo de API.
2. **BFF** *(Backend for Frontend)* — camada intermediária entre o front-end e os serviços de dados.
3. **Keycloak** — autenticação e controle de acesso.

## Tecnologias

- [Vue 3](https://vuejs.org/) (Composition API + `<script setup>`)
- [TypeScript](https://www.typescriptlang.org/)
- [Vite](https://vite.dev/) — build tool e servidor de desenvolvimento
- [Vue Router](https://router.vuejs.org/) — navegação entre páginas
- [Axios](https://axios-http.com/) — requisições HTTP
- [Pinia](https://pinia.vuejs.org/) — gerenciamento de estado (instalado, hoje sem stores em uso)

## Pré-requisitos

- [Node.js](https://nodejs.org/) instalado (recomendado LTS mais recente)
- Acesso à API do backend configurada em `vite.config.ts` (proxy `/api`)

## Como rodar o projeto

```bash
# instalar as dependências
npm install

# subir o servidor de desenvolvimento
npm run dev
```

O site abre por padrão em `http://localhost:5173`.

Outros comandos úteis:

```bash
npm run build        # gera a versão de produção (pasta dist/)
npm run type-check   # verifica erros de TypeScript no projeto inteiro
```

## Estrutura do projeto

```
src/
├── components/        # peças reutilizáveis de interface
│   ├── AppNavbar.vue        # menu de navegação do topo
│   ├── StarfieldBackground.vue  # fundo de estrelas animado
│   ├── RandomPlanet.vue     # planeta de fundo (textura real + parallax no mouse)
│   ├── HyperspaceJump.vue   # animação de salto para o hiperespaço (canvas)
│   ├── HeroCard.vue         # card de personagem no catálogo
│   ├── HeroProfile.vue      # "ficha tática" completa de um personagem
│   ├── StatBar.vue          # barra de atributo (powerstats)
│   └── AppHeader.vue        # cabeçalho simples (legado)
│
├── views/              # páginas completas, uma por rota
│   ├── IntroCrawlView.vue   # letreiro de abertura (rota "/")
│   ├── WelcomeView.vue      # menu principal (rota "/welcome")
│   ├── HomeView.vue         # catálogo de personagens (rota "/home")
│   ├── HeroDetailView.vue   # detalhe de um personagem (rota "/hero/:name")
│   ├── AboutView.vue        # sobre o projeto (rota "/sobre")
│   └── Participantes.vue    # cards da equipe (rota "/part")
│
├── router/
│   └── index.ts        # mapa de rotas do site
│
├── services/
│   └── api.ts           # instância do Axios usada para falar com a API
│
├── App.vue               # componente raiz + estilos globais (fonte, reset CSS)
└── main.ts                # ponto de entrada da aplicação

public/
├── fonts/               # fonte "Star Jedi" usada nos títulos
├── planets/             # texturas de planetas (Solar System Scope, CC BY 4.0)
└── imagem/               # fotos/avatares da página de Participantes
```

## Rotas do site

| Rota           | Página               | Descrição                                              |
|-----------------|----------------------|----------------------------------------------------------|
| `/`             | Intro / Crawl        | Letreiro de abertura; pula com a barra de espaço          |
| `/welcome`      | Menu principal        | Hero com planeta de fundo e botão para o catálogo         |
| `/home`         | Catálogo              | Grade de personagens, puxados da API                      |
| `/hero/:name`   | Detalhe do personagem | Ficha com atributos (powerstats)                           |
| `/sobre`        | Sobre o projeto       | Contexto do workshop e do MackLeaps                        |
| `/part`         | Participantes         | Cards da equipe, com modal expandido ao clicar             |

## Conectando com a API

A URL real da API fica configurada em `vite.config.ts`, dentro de `server.proxy`:

```ts
'/api': {
  target: 'https://mackleaps.mackenzie.br/wttapi',
  changeOrigin: true,
  rewrite: (path) => path.replace(/^\/api/, ''),
},
```

Em desenvolvimento, toda chamada feita pelo front-end para `/api/...` é redirecionada pelo Vite para a API real — isso evita problemas de CORS, já que o navegador enxerga tudo vindo do mesmo domínio (`localhost`).

## Créditos

- Fonte **Star Jedi** — usada nos títulos temáticos.
- Texturas de planetas — [Solar System Scope](https://www.solarsystemscope.com/textures/) (licença CC BY 4.0).
- Dados dos personagens — API fornecida pelo workshop MackLeaps.
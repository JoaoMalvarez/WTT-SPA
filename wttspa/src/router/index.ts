// ==========================================================================
// router/index.ts
// --------------------------------------------------------------------------
// Este arquivo é o "mapa do site". Ele diz ao Vue Router: "quando a pessoa
// visitar essa URL (path), mostra este componente (component)".
//
// É graças a esse arquivo que, numa SPA, trocar de "página" não recarrega
// o navegador inteiro — o Vue simplesmente troca qual componente aparece
// dentro do <RouterView /> (ver App.vue).
// ==========================================================================

// Cada "View" é um componente que representa uma página inteira do site
import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import WelcomeView from '../views/WelcomeView.vue'
import IntroCrawlView from '../views/IntroCrawlView.vue'
import AboutView from '../views/AboutView.vue'
/* 
===========================================================================================
TODO 1.1
  - faça um 'import' do arquivo 'Participantes' - pois esse é o arquivo que queremos 
  acessar detro do site
  - não esqueça de colocar onde ele está - exemplo: o 'AboutView' está no 
  '../views/AboutView.vue' (essa é em si a rota)
  - (dica: o 'Participantes' está na pasta 'views')
===========================================================================================
*/
const router = createRouter({
  // createWebHistory usa URLs "normais" (ex: seusite.com/home), sem
  // aquele # feio no meio do endereço.
  history: createWebHistory(import.meta.env.BASE_URL),

  // "routes" é a lista de todas as rotas (páginas) que o site tem.
  // Cada rota é um objeto com:
  //   - path: o endereço na URL (o que aparece depois do domínio)
  //   - name: um "apelido" pra essa rota, útil pra navegar por código
  //           (ex: router.push({ name: 'home' }) em vez de '/home')
  //   - component: qual componente Vue deve aparecer nessa rota
  routes: [
    {
      path: '/',
      name: 'intro',
      component: IntroCrawlView, // o letreiro (crawl) de abertura, primeira tela do site
    },
    {
      path: '/welcome',
      name: 'welcome',
      component: WelcomeView, // o "menu principal" (Bem-vindo, viajante da galáxia)
    },
    {
      path: '/home',
      name: 'home',
      component: HomeView, // o catálogo com todos os personagens
    },
    {
      path: '/hero/:name',
      name: 'hero-detail',
      // Repara o ":name" no path acima — isso é um "parâmetro de rota".
      // Ele captura o que vier depois de /hero/ na URL (ex: /hero/yoda)
      // e disponibiliza esse valor dentro do componente via useRoute().
      //
      // O import também está diferente aqui: em vez de importar lá em cima
      // com os outros, usamos "() => import(...)" (import dinâmico/lazy).
      // Isso faz esse componente só ser baixado pelo navegador quando a
      // pessoa realmente visitar essa página, deixando o carregamento
      // inicial do site mais rápido.
      component: () => import('../views/HeroDetailView.vue'),
    },
    {
      path: '/sobre',
      name: 'about',
      component: AboutView,
    },
    /* 
    ===========================================================================================
    TODO 1.2 (sua missão atual): fazer um router de participantes
    dicas: - faça primeiro o TODO 1.1
           - faça entre '{}' um 'path:', um 'name', e o 'component' assim como nos anterirores
           - coloque os nomes que quiser no 'path' e no 'name', mas não esqueça deles, você vai 
            precisar deles no futuro
    ===========================================================================================
    */
  ],
})

export default router
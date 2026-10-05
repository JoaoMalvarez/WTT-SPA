<!--
  ============================================================================
  AppNavbar.vue
  ----------------------------------------------------------------------------
  Este componente é o menu de navegação (a barra do topo) que aparece
  igual em várias páginas do site: logo, links de navegação e o badge do
  workshop. Por ser um componente à parte, qualquer mudança feita aqui
  aparece automaticamente em TODAS as páginas que usam <AppNavbar />,
  sem precisar editar cada página uma por uma.
  ============================================================================
-->
<script setup lang="ts">
// RouterLink é o "botão de navegação" do Vue Router, em vez de recarregar 
// a página inteira, ele troca de rota "por dentro" da SPA (mais rápido e sem 
// piscar a tela).
// useRoute() é uma função que devolve informações sobre a rota ATUAL
// (a página em que o usuário está agora). A gente usa isso aqui pra saber
// qual link do menu deve ficar destacado como "ativo".
import { RouterLink, useRoute } from 'vue-router'

const route = useRoute()
</script>

<template>
  <nav class="navbar">
    <div class="container nav-content">
      <div class="logo">Mack<span class="red-text">Leaps</span> HQ</div>

      <div class="nav-links">
        <!-- :class="{ active: route.name === 'welcome' }" é uma "classe
             condicional": a classe CSS "active" só é aplicada quando a
             condição dentro das chaves for verdadeira (ou seja, quando a
             rota atual for a 'welcome'). É assim que o link da página em
             que você está fica com um sublinhado diferente dos outros. -->
        <RouterLink to="/welcome" class="nav-link" :class="{ active: route.name === 'welcome' }">
          Home
        </RouterLink>
        <RouterLink to="/home" class="nav-link" :class="{ active: route.name === 'home' }">
          Catálogo de Personagens
        </RouterLink>
        <RouterLink to="/sobre" class="nav-link" :class="{ active: route.name === 'about' }">
          Sobre o Projeto
        </RouterLink>
        <!-- 
        ==========================================================================
        TODO 3: completar o RouterLink abaixo (ou seja um botão no Navbar!)
          - to="(path que você definiu no @/router/index.ts)"
          - :class="{ active: route.name === '(name que você definiu no @/router/index.ts)'}"
          - dica: o RouterLink já está criado, só falta as informações de onde ir ao clicar
        ==========================================================================
          -->
        <RouterLink to="/part" class="nav-link" :class="{ active: route.name === 'part'}"> 
          Participantes
        </RouterLink>   
      </div>

      <div class="status-badge">Workshop 1: Aprendendo SPA com Vue</div>
    </div>
  </nav>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Anton&display=swap');

.navbar {
  position: relative;
  z-index: 1;
  padding: 20px 0;
  border-bottom: 1px solid rgba(255, 255, 255, 0.08);
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  width: 100%;
}

/* grid-template-columns: 1fr auto 1fr cria 3 colunas: a primeira e a
   terceira SEMPRE do mesmo tamanho entre si (1fr cada), e a do meio só
   ocupa o espaço que o conteúdo dela precisa (auto). Isso garante que o
   menu do meio fique realmente centralizado na tela, mesmo o logo (esquerda)
   e o badge (direita) tendo tamanhos bem diferentes um do outro. */
.nav-content {
  display: grid;
  grid-template-columns: 1fr auto 1fr;
  align-items: center;
  gap: 16px;
}

.logo {
  font-family: 'Anton', sans-serif;
  color: #ffe81f;
  font-size: 1.2rem;
  letter-spacing: 1px;
  line-height: 1;
}

.red-text {
  color: #bc1e22;
}

.nav-links {
  display: flex;
  align-items: center;
  gap: 28px;
}

.nav-link {
  color: #8fb4d9;
  text-decoration: none;
  font-size: 0.9rem;
  padding-bottom: 4px;
  border-bottom: 2px solid transparent;
  line-height: 1;
  transition: color 0.2s ease;
}

.nav-link:hover {
  color: #ffe81f;
}

/* Esta classe só é aplicada quando a condição lá no template (:class)
   é verdadeira - troca só a COR da borda, sem mudar a espessura, pra
   evitar que os itens do menu "pulem" de posição ao trocar de página. */
.nav-link.active {
  color: #ffe81f;
  border-bottom-color: #ffe81f;
}

.status-badge {
  background-color: rgba(188, 30, 34, 0.15);
  border: 1px solid rgba(188, 30, 34, 0.4);
  color: #f19292;
  padding: 6px 12px;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: 600;
  line-height: 1;
  justify-self: end; /* gruda o badge na ponta direita da sua coluna do grid */
}

/* @media é uma "regra condicional" de CSS: o que está dentro dela só se
   aplica quando a largura da tela for menor ou igual a 700px (celulares,
   por exemplo). Isso é o que chamamos de design "responsivo". */
@media (max-width: 700px) {
  .nav-links {
    order: 3;
    width: 100%;
    justify-content: center;
  }
}
</style>
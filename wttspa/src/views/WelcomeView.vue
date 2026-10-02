<!--
  ============================================================================
  WelcomeView.vue
  ----------------------------------------------------------------------------
  A tela de "menu principal" do site (rota "/welcome"), chamada de "Home" no
  menu de navegação. É aqui que fica o planeta de fundo e o botão "Iniciar"
  que leva pro catálogo de personagens.
  ============================================================================
-->
<script setup lang="ts">
import { RouterLink, useRouter } from 'vue-router'
import AppNavbar from '@/components/AppNavbar.vue'
import RandomPlanet from '@/components/RandomPlanet.vue'

const router = useRouter()

// Função simples chamada pelo clique no botão "Iniciar": navega o usuário
// pra rota "/home" (o catálogo de personagens) via código, sem precisar de
// um <RouterLink> de verdade nesse caso.
const goToCatalog = () => {
  router.push('/home')
}

// Essa parte vai criar as estrelas de fundo
const stars = Array.from({ length: 120 }, (_, i) => ({
  id: i,
  style: {
    top: `${Math.random() * 100}%`,
    left: `${Math.random() * 100}%`,
    width: `${Math.random() * 2 + 1}px`,
    height: `${Math.random() * 2 + 1}px`,
    animationDelay: `${Math.random() * 4}s`,
    animationDuration: `${Math.random() * 3 + 2}s`,
  },
}))
</script>

<template>
    <div class="welcome-page">
    <div class="starfield">
      <span v-for="star in stars" :key="star.id" class="star" :style="star.style"></span>
    </div>

    <RandomPlanet />
    
  <AppNavbar />
    <section class="hero">
      <h1 class="hero-title">BEM-viNDoS, viAJANTES DA GALÁxiA</h1>
      <p class="hero-subtitle">
        Há muito tempo, em uma galáxia muito, muito distante... um arquivo vivo de heróis, vilões
        e criaturas aguarda para ser explorado. Prepare-se para conhecer os personagens que
        moldaram o destino das estrelas.
      </p>

      <p class="hero-hint">
        A galáxia espera por você. Toque para revelar o arquivo completo de personagens.
      </p>

      <button class="btn-start" @click="goToCatalog">Iniciar</button>
    </section>
  </div>
</template>

<style scoped>

.welcome-page {
  position: relative;
  min-height: 100vh;
  background-color: #050505;
  font-family: 'Franklin Gothic Demi', 'Arial Narrow', sans-serif;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.starfield {
  position: absolute;
  inset: 0;
  z-index: 0;
}

.star {
  position: absolute;
  background: #ffffff;
  border-radius: 50%;
  animation-name: twinkle;
  animation-iteration-count: infinite;
  animation-timing-function: ease-in-out;
}

@keyframes twinkle {
  0%, 100% { opacity: 0.2; }
  50% { opacity: 1; }
}

.hero {
  position: relative;
  z-index: 1; /* fica acima do planeta e das estrelas (que ficam no z-index 0) */
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  padding: 60px 24px 100px;
}

.hero-title {
  font-family: 'Star Jedi', 'Anton', sans-serif;
  color: #ffe81f;
  font-size: clamp(2.2rem, 6vw, 4rem); /* tamanho de fonte "elástico": nunca menor que 2.2rem nem maior que 4rem */
  letter-spacing: 1px;
  max-width: 900px;
  margin: 0 0 30px;
  line-height: 1.1;
}

.hero-subtitle {
  color: #ffe81f;
  opacity: 0.85;
  font-size: 1.15rem;
  max-width: 680px;
  line-height: 1.6;
  margin: 0 0 70px;
}

.hero-hint {
  color: #7ca6cf;
  font-size: 1.05rem;
  max-width: 500px;
  line-height: 1.5;
  margin: 0 0 30px;
}

.btn-start {
  background-color: #ffe81f;
  color: #111;
  border: none;
  padding: 16px 48px;
  border-radius: 10px;
  font-weight: 700;
  font-size: 1.05rem;
  font-family: inherit;
  cursor: pointer;
  box-shadow: 0 8px 20px rgba(255, 232, 31, 0.15);
  transition: transform 0.2s ease;
}

.btn-start:hover {
  transform: translateY(-2px);
}

</style>

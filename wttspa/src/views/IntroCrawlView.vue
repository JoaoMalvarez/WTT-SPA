<!--
  ============================================================================
  IntroCrawlView.vue
  ----------------------------------------------------------------------------
  Primeira tela que a pessoa vê ao abrir o site (rota "/"). Mostra a frase
  de abertura, depois o letreiro amarelo em perspectiva (tipo Star Wars),
  e por fim dispara o efeito de salto pro hiperespaço antes de navegar pra
  "/welcome".
  ============================================================================
-->
<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { useRouter } from 'vue-router'
import HyperspaceJump from '@/components/HyperspaceJump.vue'

const router = useRouter()

// ref() cria uma variável "reativa": quando o valor dela muda, o Vue
// automaticamente atualiza qualquer parte do <template> que dependa dela
// (sem precisar dar refresh na página). Aqui, "stage" controla qual das
// duas partes do crawl está sendo mostrada no momento.
//
// 'opening' -> frase azul de abertura
// 'crawl'   -> letreiro amarelo em perspectiva
const stage = ref<'opening' | 'crawl'>('opening')

let openingTimer: ReturnType<typeof setTimeout>
let crawlTimer: ReturnType<typeof setTimeout>

// "jumping" controla se o efeito de hiperespaço (HyperspaceJump.vue) deve
// ser exibido por cima de tudo agora.
const jumping = ref(false)

function startJump() {
  if (jumping.value) return // evita disparar duas vezes
  jumping.value = true
}

// Chamada pelo HyperspaceJump quando ele termina (evento @done) - só então
// a gente de fato troca de página.
function finishJump() {
  router.push('/welcome')
}

// Cancela os timers pendentes e já dispara o salto - usada tanto pelo
// clique na tela quanto pela tecla espaço.
function skip() {
  clearTimeout(openingTimer)
  clearTimeout(crawlTimer)
  startJump()
}

// "Escuta" o teclado inteiro da página: se a tecla apertada for a barra de
// espaço, chama skip(). e.preventDefault() evita que a página role pra
// baixo (comportamento padrão do navegador ao apertar espaço).
function handleKeydown(e: KeyboardEvent) {
  if (e.code === 'Space') {
    e.preventDefault()
    skip()
  }
}

// Essa parte vai criar as estrelas (mesma lógica do StarfieldBackground.vue)
const stars = Array.from({ length: 140 }, (_, i) => ({
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

onMounted(() => {
  window.addEventListener('keydown', handleKeydown)

  // Depois de 5 segundos mostrando a frase de abertura, troca pro letreiro
  openingTimer = setTimeout(() => {
    stage.value = 'crawl'
    // Duração da animação do crawl (ver CSS) + folga antes de navegar
    crawlTimer = setTimeout(startJump, 42000)
  }, 5000)
})

onBeforeUnmount(() => {
  window.removeEventListener('keydown', handleKeydown)
  clearTimeout(openingTimer)
  clearTimeout(crawlTimer)
})
</script>

<template>
  <!-- @click="skip" no container inteiro: clicar em qualquer lugar da tela
       também pula o crawl, não só apertando espaço -->
  <div class="crawl-screen" @click="skip">
    <div class="starfield">
      <span v-for="star in stars" :key="star.id" class="star" :style="star.style"></span>
    </div>

    <!-- <transition name="fade"> envolve um elemento que entra/sai da tela
         (aqui, controlado pelo v-if) e aplica uma animação suave de
         entrada/saída usando as classes .fade-enter-active/.fade-leave-active
         definidas lá no <style>, em vez do elemento simplesmente
         aparecer/sumir de repente. -->
    <transition name="fade">
      <p v-if="stage === 'opening'" class="opening-line">
        Há muito tempo, em uma SPA muito, muito distante....
      </p>
    </transition>

    <div v-if="stage === 'crawl'" class="crawl-viewport">
      <div class="crawl-text">
        <h2>Episódio I</h2>
        <h1>O DESPERTAR DO FRONTEND</h1>
        <p>
          Nos confins da Rede República, um grupo de padawans se reúne para sua primeira grande
          missão: aprender a construir Single Page Applications com Vue.
        </p>
        <p>
          Sob a orientação do laboratório MackLeaps, eles enfrentarão desafios de roteamento,
          chamadas à API e batalhas contra bugs traiçoeiros escondidos no código.
        </p>
        <p>
          Mas a maior missão ainda os aguarda: preencher os espaços em branco, treinar seus
          poderes e revelar todos os personagens registrados na galáxia....
        </p>
      </div>
    </div>

    <button class="skip-hint" @click.stop="skip">Pressione ESPAÇO para pular</button>

    <!-- HyperspaceJump só existe na tela enquanto "jumping" for true.
         Quando ele termina a animação, dispara @done="finishJump", que
         navega de verdade pra próxima página. -->
    <HyperspaceJump v-if="jumping" @done="finishJump" />
  </div>
</template>

<style scoped>
.crawl-screen {
  position: relative;
  width: 100%;
  height: 100vh;
  background: #000;
  overflow: hidden;
  cursor: pointer;
}

.starfield {
  position: absolute;
  inset: 0;
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

.opening-line {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  max-width: 600px;
  text-align: center;
  color: #5ce1e6;
  font-family: 'Franklin Gothic Demi', 'Arial Narrow', sans-serif;
  font-size: 1.8rem;
  letter-spacing: 1px;
  margin: 0;
  padding: 0 20px;
}

/* Classes usadas pelo <transition name="fade"> lá no template - o Vue
   aplica ".fade-enter-active"/".fade-leave-active" durante a transição, e
   ".fade-enter-from"/".fade-leave-to" no estado inicial/final dela. */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 1.5s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.crawl-viewport {
  position: absolute;
  inset: 0;
  overflow: hidden;
  perspective: 400px; /* necessário pro rotateX() do .crawl-text parecer 3D de verdade */
}

.crawl-text {
  position: absolute;
  left: 50%;
  bottom: 0;
  width: min(800px, 85vw);
  transform-origin: 50% 100%;
  animation: crawl 45s linear forwards;
  color: #ffe81f;
  font-family: 'Franklin Gothic Demi', 'Arial Narrow', sans-serif;
  text-align: justify;
  line-height: 1.7;
}

.crawl-text h2 {
  text-align: center;
  font-size: 2rem;
  margin: 0 0 10px;
}

.crawl-text h1 {
  text-align: center;
  font-size: 3.2rem;
  margin: 0 0 50px;
  letter-spacing: 2px;
}

.crawl-text p {
  font-size: 1.8rem;
  margin: 0 0 45px;
}

/* A animação do letreiro: começa grande (scale 1.6) e embaixo da tela
   (translate Y positivo), e termina pequeno (scale 0.4) e lá no topo
   (translate Y negativo) - simulando o texto "se afastando no horizonte" */
@keyframes crawl {
  0% {
    transform: translate(-50%, 120vh) rotateX(25deg) scale(1.6);
  }
  100% {
    transform: translate(-50%, -170%) rotateX(25deg) scale(0.4);
  }
}

.skip-hint {
  position: absolute;
  bottom: 24px;
  right: 24px;
  background: transparent;
  border: 1px solid rgba(255, 232, 31, 0.4);
  color: #ffe81f;
  font-family: 'Franklin Gothic Demi', 'Arial Narrow', sans-serif;
  font-size: 0.8rem;
  letter-spacing: 1px;
  padding: 8px 14px;
  border-radius: 4px;
  cursor: pointer;
  z-index: 5;
}

.skip-hint:hover {
  background: rgba(255, 232, 31, 0.1);
}
</style>

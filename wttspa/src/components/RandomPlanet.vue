<!--
  ============================================================================
  RandomPlanet.vue
  ----------------------------------------------------------------------------
  Desenha um planeta de fundo (com foto de textura real) atrás do conteúdo
  da WelcomeView. A cada vez que a página é carregada, um planeta diferente
  é sorteado da lista, e o planeta "gira" (desliza a textura) conforme o
  mouse se move na horizontal.
  ============================================================================
-->
<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue'

interface PlanetType {
    texture: string
    size: number
    hasRing: boolean
    ringColor: string
}
// texturas dos planetas de fundo (eles pegam dentro wttspa/public/planets)
const planetTypes: PlanetType[] = [
    // textura = jpg do planeta | size = tamanho na tela | hasRing = se tem anel | ringColor = cor do anel
    { texture: '/planets/2k_ceres_fictional.jpg', size: 520, hasRing: false, ringColor: '' },
    { texture: '/planets/2k_death_star.jpg', size: 600, hasRing: false, ringColor: '' },
    { texture: '/planets/2k_earth_nightmap.jpg', size: 440, hasRing: false, ringColor: '' },
    { texture: '/planets/2k_eris_fictional.jpg', size: 560, hasRing: false, ringColor: '' },
    { texture: '/planets/2k_haumea_fictional.jpg', size: 480, hasRing: false, ringColor: '' },
    { texture: '/planets/2k_makemake_fictional.jpg', size: 460, hasRing: false, ringColor: '' },
    { texture: '/planets/2k_mercury.jpg', size: 460, hasRing: false, ringColor: ''},
    { texture: '/planets/2k_moon.jpg', size: 100, hasRing: false, ringColor: ''},
    { texture: '/planets/2k_neptune.jpg', size: 560, hasRing: false, ringColor: ''},
]

// Sorteia um planeta quando a página carrega.
// Math.random() devolve um número decimal entre 0 e 1. Multiplicando pelo
// tamanho da lista e arredondando pra baixo (Math.floor), a gente consegue
// um índice aleatório válido dentro do array - e assim pegamos um planeta
// diferente a cada vez que esse componente é criado (cada visita à página).
// O "!" no final avisa o TypeScript que esse valor nunca vai ser undefined.
const chosen = planetTypes[Math.floor(Math.random() * planetTypes.length)]!
const bgX = ref(0)

// Quando o mouse se mexer o planeta se mexe junto.
// e.clientX é a posição horizontal do mouse em pixels na tela toda.
// Dividindo por window.innerWidth (a largura total da tela), "relX" vira
// um número entre 0 (mouse na borda esquerda) e 1 (mouse na borda direita).
// Multiplicando por -250, transformamos isso num deslocamento de textura -
// esse valor muda o background-position-x lá no template, criando a
// sensação de rotação.
function handleMouseMove(e: MouseEvent) {
  const relX = e.clientX / window.innerWidth
  bgX.value = relX * -250
}

// onMounted roda uma função assim que o componente é "montado" (aparece de
// verdade na tela). Aqui usamos isso pra começar a "escutar" o movimento do
// mouse na janela inteira.
onMounted(() => {
  window.addEventListener('mousemove', handleMouseMove)
})

// onBeforeUnmount roda pouco antes do componente ser destruído (ex: quando
// você navega pra outra página e esse componente deixa de existir). É
// importante remover o "escutador" de evento aqui, senão ele continuaria
// rodando escondido na memória mesmo depois da página ter mudado (isso se
// chama "memory leak" / vazamento de memória).
onBeforeUnmount(() => {
  window.removeEventListener('mousemove', handleMouseMove)
})
</script>

<template>
    <!-- Vai criar o template do planeta com o 'planet-wrapper' a textura do planeta (imagem) -->
    <div class="planet-wrapper" :style="{ width: chosen.size + 'px', height: chosen.size + 'px' }">
        <!-- v-if só desenha este <div> na tela se chosen.hasRing for true
             (ou seja, só os planetas marcados com anel no array lá em cima
             ganham essa camada extra) -->
        <div
        v-if="chosen.hasRing"
        class="ring"
        :style="{ borderColor: chosen.ringColor }"
        ></div>

        <div
        class="planet-sphere"
        :style="{
            backgroundImage: `url('${chosen.texture}')`,
            backgroundPositionX: bgX + 'px',
        }"
        ></div>

        <div class="shading"></div>
    </div>
</template>

<style scoped>
.planet-wrapper {
    position: absolute;
    top: 5%;
    right: -8%;
    z-index: 0;
    pointer-events: none;
}

/* Definição da esfera do planeta */
.planet-sphere {
    position: absolute;
    inset: 0;
    border-radius: 50%;
    background-repeat: repeat-x;
    background-size: auto 100%;
    background-position-y: center;
    transition: background-position-x 0.9s ease-out;
}

/* Camada de sombreamento por cima da textura: cria o efeito de "lado escuro"
   do planeta (terminador) e um leve brilho atmosférico, independente da rotação */
.shading {
    position: absolute;
    inset: 0;
    border-radius: 50%;
    pointer-events: none;
    background:
        /* brilho do "sol", vindo de um canto */
        radial-gradient(circle at 30% 25%, rgba(255, 255, 255, 0.3), transparent 42%),
        /* vinheta: escurece as bordas por igual, dando a curvatura da esfera */
        radial-gradient(circle at 50% 50%, transparent 52%, rgba(0, 0, 0, 0.65) 100%),
        /* lado escuro / terminador, pra dar direção de luz */
        linear-gradient(115deg, transparent 45%, rgba(0, 0, 0, 0.8) 85%, rgba(0, 0, 0, 0.95) 100%);
    box-shadow:
        inset 0 0 50px rgba(0, 0, 0, 0.55),
        0 0 90px rgba(140, 180, 255, 0.2);
}

/* Anel */
.ring {
    position: absolute;
    top: 50%;
    left: 50%;
    width: 190%;
    height: 34%;
    border: 14px solid;
    border-radius: 50%;
    transform: translate(-50%, -50%) rotate(-16deg);
    z-index: -1;
    opacity: 0.8;
}
</style>
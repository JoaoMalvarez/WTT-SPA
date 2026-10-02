<!--
  ============================================================================
  HyperspaceJump.vue
  ----------------------------------------------------------------------------
  Desenha a animação de "salto pro hiperespaço" (aqueles riscos de luz que
  passam voando pela tela) usando um <canvas> - uma área da página onde a
  gente desenha manualmente usando código JavaScript, quadro a quadro, como
  uma animação desenhada à mão em alta velocidade.

  Este componente aparece por cima de tudo (z-index bem alto) por 1,4
  segundos, e depois avisa quem o está usando (IntroCrawlView) que já
  terminou, através de um "emit" (evento customizado).
  ============================================================================
-->
<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue'

// defineEmits() declara quais EVENTOS este componente pode "disparar" pra
// fora dele. Pense assim: props são dados que ENTRAM no componente (de pai
// pra filho); emits são "avisos" que SAEM do componente (de filho pra pai).
// Aqui declaramos que este componente pode emitir um evento chamado 'done'.
const emit = defineEmits<{ (e: 'done'): void }>()

// ref() cria uma "referência" que o Vue consegue conectar direto a um
// elemento HTML de verdade na tela (veja o ref="canvasRef" no template).
// Assim a gente consegue pegar o elemento <canvas> real e desenhar nele
// via JavaScript.
const canvasRef = ref<HTMLCanvasElement | null>(null)
let animationFrame: number
let startTime = 0
let doneTimer: ReturnType<typeof setTimeout>

// Descreve um "risco" de luz individual: de qual ângulo ele sai, com que
// velocidade, distância inicial do centro, cor e espessura.
interface Streak {
    angle: number
    r0: number // posição inicial (já distribuída por toda a tela, não só no centro)
    speed: number
    shade: string
    width: number
}

let streaks: Streak[] = []
let maxRadius = 0

// setup() prepara os dados de cada risco de luz ANTES da animação começar
// (roda uma única vez). Ajustar o canvas pro tamanho da tela também é feito
// aqui.
function setup(canvas: HTMLCanvasElement) {
    canvas.width = window.innerWidth
    canvas.height = window.innerHeight
    maxRadius = Math.hypot(canvas.width, canvas.height) / 2

    // Cria 2000 riscos de luz, cada um com valores aleatórios diferentes
    streaks = Array.from({ length: 2000 }, () => {
        const roll = Math.random()
        return {
            angle: Math.random() * Math.PI * 2, // ângulo aleatório (em radianos, 0 a 360°)
            // Math.pow(Math.random(), 3) concentra a maioria dos valores
            // perto de 0 (ou seja, perto do centro da tela), criando o
            // efeito de "funil" em vez de uma distribuição totalmente
            // espalhada
            r0: Math.pow(Math.random(), 3) * maxRadius,
            speed: 500 + Math.random() * 1600,
            shade: roll > 0.85 ? 'rgba(160,190,255,0.9)' : roll > 0.7 ? 'rgba(200,200,210,0.85)' : '#ffffff',
            width: 1 + Math.random() * 1.6,
        }
    })
}

// draw() é chamada repetidamente, quadro a quadro (a cada "frame" de
// animação do navegador, normalmente 60 vezes por segundo), e é ela que
// efetivamente desenha os riscos na tela.
function draw(timestamp: number) {
    const canvas = canvasRef.value
    if (!canvas) return
    const ctx = canvas.getContext('2d') // "ctx" é o "pincel" que desenha no canvas
    if (!ctx) return

    if (!startTime) startTime = timestamp
    const elapsed = (timestamp - startTime) / 1000 // segundos desde o início da animação

    // Em vez de limpar o canvas 100% a cada frame, pintamos uma camada
    // preta SEMI-transparente por cima - isso deixa um leve "rastro" de
    // movimento nos riscos, em vez deles desaparecerem instantaneamente.
    ctx.fillStyle = 'rgba(0, 0, 0, 0.5)'
    ctx.fillRect(0, 0, canvas.width, canvas.height)

    const cx = canvas.width / 2
    const cy = canvas.height / 2
    const accel = 1 + elapsed * elapsed * 5 // acelera com o tempo (cresce ao quadrado, não em linha reta)
    const fadeIn = Math.min(1, elapsed * 5) // os riscos "aparecem" gradualmente logo no início

    ctx.lineCap = 'round'

    // Percorre cada risco de luz e desenha uma linha, do ponto "interno"
    // até o ponto "externo" (que vai ficando mais longe do centro conforme
    // o tempo passa)
    streaks.forEach((s) => {
        const outer = s.r0 + elapsed * s.speed * accel
        // Comprimento proporcional à distância percorrida: perto do centro = curto, longe = bem comprido
        const trailLen = outer * 0.4 + 20
        const inner = Math.max(0, outer - trailLen)

        // Math.cos/Math.sin convertem "ângulo + distância" em coordenadas
        // X/Y de verdade na tela (trigonometria básica de círculo)
        const x1 = cx + Math.cos(s.angle) * inner
        const y1 = cy + Math.sin(s.angle) * inner
        const x2 = cx + Math.cos(s.angle) * outer
        const y2 = cy + Math.sin(s.angle) * outer

        ctx.strokeStyle = s.shade
        ctx.lineWidth = s.width
        ctx.globalAlpha = fadeIn
        ctx.beginPath()
        ctx.moveTo(x1, y1)
        ctx.lineTo(x2, y2)
        ctx.stroke()
    })

    // Flash branco no instante do "salto" (últimos instantes da animação)
    if (elapsed > 1.05) {
        const flashProgress = Math.min(1, (elapsed - 1.05) / 0.3)
        ctx.globalAlpha = flashProgress
        ctx.fillStyle = '#ffffff'
        ctx.fillRect(0, 0, canvas.width, canvas.height)
    }

    ctx.globalAlpha = 1

    // Só continua pedindo o próximo frame enquanto a animação não tiver
    // passado de 1.4 segundos - depois disso, o desenho simplesmente para.
    if (elapsed < 1.4) {
        animationFrame = requestAnimationFrame(draw)
    }
}

onMounted(() => {
    const canvas = canvasRef.value
    if (canvas) {
        setup(canvas)
        animationFrame = requestAnimationFrame(draw) // inicia o loop de desenho
    }

    // Depois de 1450ms (um pouco mais que a duração da animação em si),
    // emite o evento 'done' - é isso que avisa a IntroCrawlView.vue que
    // já pode navegar pra tela seguinte (/welcome).
    doneTimer = setTimeout(() => {
        emit('done')
    }, 1450)
})

onBeforeUnmount(() => {
    cancelAnimationFrame(animationFrame) // cancela o loop de desenho, se ainda estiver rodando
    clearTimeout(doneTimer)
})
</script>

<template>
    <div class="hyperspace">
        <canvas ref="canvasRef"></canvas>
    </div>
</template>

<style scoped>
.hyperspace {
    position: fixed; /* cobre a tela inteira, por cima de absolutamente tudo */
    inset: 0;
    background: #000;
    z-index: 999;
}

canvas {
    width: 100%;
    height: 100%;
    display: block;
}
</style>
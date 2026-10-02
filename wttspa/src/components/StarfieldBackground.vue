<!--
  ============================================================================
  StarfieldBackground.vue
  ----------------------------------------------------------------------------
  Este é um COMPONENTE REUTILIZÁVEL: um pedacinho de interface que pode ser
  "chamado" dentro de várias páginas diferentes (Home, Welcome, About, etc.)
  sem precisar copiar e colar o mesmo código em cada uma.

  A função dele é simples: desenhar um fundo de estrelas piscando, usado
  atrás do conteúdo em quase todas as páginas do site.
  ============================================================================
-->
<script setup lang="ts">
// Essa parte vai criar as estrelas.
//
// Array.from({ length: 120 }, ...) cria uma lista (array) com 120 posições,
// e a segunda função é chamada uma vez pra cada posição — é assim que a
// gente gera 120 estrelas diferentes, cada uma com sua própria posição e
// tamanho aleatórios (Math.random() devolve um número entre 0 e 1).
//
// Cada estrela vira um objeto com um "id" (necessário pro Vue identificar
// cada uma no v-for lá no template) e um "style" (um objeto de estilos CSS
// que vamos aplicar direto na tag, via :style, no template).
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
  <div class="starfield">
    <!-- v-for="star in stars" repete esta <span> uma vez para cada item
         dentro do array "stars" que criamos ali em cima. É assim que o
         Vue transforma uma lista de dados em elementos na tela, sem a
         gente precisar escrever 120 <span> manualmente.

         :key="star.id" ajuda o Vue a identificar cada estrela individualmente
         (é uma boa prática obrigatória sempre que se usa v-for).

         :style="star.style" aplica o objeto de estilos daquela estrela
         específica (posição, tamanho, tempo de animação). -->
    <span v-for="star in stars" :key="star.id" class="star" :style="star.style"></span>
  </div>
</template>

<style scoped>
.starfield {
  position: absolute;
  inset: 0; /* atalho pra top:0; left:0; right:0; bottom:0 - preenche todo o espaço do pai */
  z-index: 0; /* fica "atrás" do conteúdo da página */
  pointer-events: none; /* garante que cliques "atravessem" as estrelas, sem bloquear botões por baixo */
}

.star {
  position: absolute;
  background: #ffffff;
  border-radius: 50%;
  animation-name: twinkle;
  animation-iteration-count: infinite;
  animation-timing-function: ease-in-out;
}

/* @keyframes define os "passos" de uma animação CSS: aqui, a estrela
   começa e termina com opacidade baixa (quase apagada) e no meio do
   caminho fica com opacidade máxima (bem visível) - criando o efeito
   de "piscar". */
@keyframes twinkle {
  0%, 100% { opacity: 0.2; }
  50% { opacity: 1; }
}
</style>
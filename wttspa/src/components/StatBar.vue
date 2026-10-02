<!--
  ============================================================================
  StatBar.vue
  ----------------------------------------------------------------------------
  Desenha UMA barra de atributo (ex: "FORÇA ████████░░ 80") na página de
  detalhe do personagem. O HeroProfile usa este componente várias vezes
  (um para cada atributo que vem da API), então em vez de repetir o HTML
  da barra manualmente pra cada atributo, a gente criou esse componente
  reutilizável.
  ============================================================================
-->
<script setup lang="ts">
// Aqui usamos defineProps() e guardamos o resultado numa variável "props"
// (diferente do HeroCard, que não precisou guardar o resultado em lugar
// nenhum porque só usava as props direto no <template>). Guardar em "props"
// é útil quando você também quer usar esses valores dentro do <script>,
// como fazemos logo abaixo na função getAlphaColor.
const props = defineProps<{
  label: string // nome do atributo, ex: "força"
  value: string | number // valor do atributo, ex: 80 (também vira a % da barra preenchida)
  color: string // cor em hexadecimal, usada pra pintar a barra e os textos
}>()

// Função auxiliar: recebe uma cor hexadecimal (ex: "#ffe81f") e um valor de
// transparência (ex: "80"), e devolve os dois juntos (ex: "#ffe81f80").
// Isso funciona porque hexadecimal com 8 dígitos no CSS já inclui um canal
// de transparência (alpha) nos 2 últimos caracteres.
const getAlphaColor = (hex: string, alpha: string) => `${hex}${alpha}`
</script>

<template>
  <div class="stat-item">
    <div class="stat-header">
      <span class="stat-name">
        <span class="bracket" :style="{ color: color }">⟨</span>
        {{ label.toUpperCase() }}
        <span class="bracket" :style="{ color: color }">⟩</span>
      </span>
      <span
        class="stat-value-box stat-value-box-dark"
        :style="{ color: color, borderBottomColor: color }"
      >
        {{ value }}
      </span>
    </div>

    <div class="stat-bar-container stat-bar-container-dark">
      <div class="stat-bar-track"></div>
      <!-- A barra preenchida usa o próprio "value" como porcentagem da
           largura (width: `${value}%`) - é assim que um atributo de valor
           80 preenche 80% da barra, e um de valor 30 preenche só 30%. -->
      <div
        class="stat-bar-fill"
        :style="{
          width: `${value}%`,
          backgroundColor: color,
          boxShadow: `0 0 12px ${getAlphaColor(color, '80')}`,
        }"
      ></div>
      <div class="stat-bar-segments-dark"></div>
    </div>
  </div>
</template>

<style scoped>
.stat-item {
  width: 100%;
}
.stat-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  margin-bottom: 8px;
}
.stat-name {
  font-size: 0.9rem;
  font-weight: 700;
  color: #f1f5f9;
  letter-spacing: 1px;
}
.bracket {
  opacity: 0.8;
  font-weight: 900;
  margin: 0 2px;
}
.stat-value-box-dark {
  font-size: 1.1rem;
  font-weight: 800;
  font-family: monospace;
  background: rgba(26, 26, 26, 0.8);
  padding: 2px 8px;
  border-radius: 4px;
  border-bottom: 3px solid;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}
.stat-bar-container-dark {
  position: relative;
  height: 14px;
  width: 100%;
  border-radius: 2px;
  overflow: hidden;
  background: #1a1a1a;
  box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.2);
  border: 1px solid rgba(255, 255, 255, 0.05);
}
.stat-bar-fill {
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  border-radius: 2px;
  /* transition aqui faz a barra "crescer" suavemente até a largura final,
     em vez de aparecer já do tamanho certo instantaneamente */
  transition: width 1.5s cubic-bezier(0.2, 0.8, 0.2, 1);
}
.stat-bar-segments-dark {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  /* Cria pequenas divisórias verticais repetidas por cima da barra, dando
     um visual "segmentado" tipo medidor de nível de bateria */
  background: repeating-linear-gradient(
    90deg,
    transparent,
    transparent calc(10% - 2px),
    rgba(17, 17, 17, 0.8) calc(10% - 2px),
    rgba(17, 17, 17, 0.8) 10%
  );
  z-index: 2;
}
</style>

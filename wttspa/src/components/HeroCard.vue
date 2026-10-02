<!--
  ============================================================================
  HeroCard.vue
  ----------------------------------------------------------------------------
  Representa UM card de personagem dentro do catálogo (grid da HomeView).
  Em vez da HomeView desenhar o HTML de cada card na mão, ela usa este
  componente várias vezes (um v-for lá na HomeView), passando o nome e a
  imagem de cada personagem via PROPS.
  ============================================================================
-->
<script setup lang="ts">
import { RouterLink } from 'vue-router'

// Define as propriedades (props) que este componente espera receber da View pai
// defineProps() declara quais dados este componente ESPERA RECEBER de quem
// o estiver usando (nesse caso, a HomeView). Pense em "props" como os
// parâmetros de uma função, só que para componentes: a HomeView "chama"
// <HeroCard hero-name="Yoda" hero-image="..." /> e esses valores chegam
// aqui dentro como heroName e heroImage.
defineProps<{
  heroName: string
  heroImage: string
}>()
</script>

<template>
  <!-- :style="{ backgroundImage: ... }" aplica um CSS dinamicamente, usando
      o valor da prop heroImage que recebemos - por isso cada card mostra
      uma imagem de fundo diferente, mesmo usando o mesmo componente. -->
  <div class="hero-card" :style="{ backgroundImage: `url(${heroImage})` }">
    <div class="card-overlay">
      <!-- Este RouterLink monta a URL dinamicamente: se heroName for
           "yoda", o link aponta pra "/hero/yoda" - que é exatamente a
           rota com parâmetro (:name) que vimos no router/index.ts. -->
      <RouterLink :to="`/hero/${heroName}`" class="btn-primary">
        {{ heroName }}
      </RouterLink>
    </div>
  </div>
</template>

<style scoped>
.hero-card {
  position: relative;
  height: 400px;
  border-radius: 16px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  transition:
    transform 0.3s ease,
    box-shadow 0.3s ease;
  overflow: hidden;
  background-size: cover;
  background-position: center top;
  background-repeat: no-repeat;
}

.hero-card:hover {
  transform: translateY(-8px); /* "levanta" o card um pouco ao passar o mouse */
  box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.2);
}

.card-overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 100%;
  /* gradiente escuro embaixo, ficando transparente em cima - garante que o
     texto do botão continue legível, não importa qual seja a imagem de fundo */
  background: linear-gradient(
    to top,
    rgba(0, 0, 0, 0.9) 0%,
    rgba(0, 0, 0, 0.3) 40%,
    transparent 100%
  );
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: center;
  padding: 30px 20px;
}

.btn-primary {
  display: inline-block;
  background-color: #ffe81f;
  color: #111;
  padding: 14px 24px;
  text-decoration: none;
  border-radius: 8px;
  font-weight: 800;
  font-size: 1.2rem;
  transition: background-color 0.2s ease, transform 0.2s ease;
  width: 100%;
  text-align: center;
  box-sizing: border-box;
  text-transform: uppercase;
  letter-spacing: 1px;
}

.btn-primary:hover {
  background-color: #e6d01b;
  transform: scale(1.02);
}
</style>

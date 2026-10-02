<!--
  ============================================================================
  HeroProfile.vue
  ----------------------------------------------------------------------------
  Desenha o "cartão tático" inteiro de um personagem: a foto, o nome e a
  lista de atributos (usando o StatBar várias vezes). Quem controla os
  dados (foi a API que respondeu, deu erro, ainda está carregando) é a
  HeroDetailView - este componente só recebe tudo pronto via props e decide
  o que mostrar na tela.
  ============================================================================
-->
<script setup lang="ts">
import { RouterLink } from 'vue-router'
import StatBar from '@/components/StatBar.vue'

// "interface" é um recurso do TypeScript: descreve o FORMATO que um objeto
// deve ter (quais campos existem e de que tipo). Isso não existe em
// JavaScript puro - é uma "camada extra" que ajuda a pegar erros de
// digitação/tipo antes mesmo de rodar o código (ex: se você tentar acessar
// hero.nome em vez de hero.name, o editor já avisa que esse campo não existe).
interface CharacterDTO {
  name: string
  image: string
  mainColor: string
  powerstats: Record<string, string> // um objeto tipo { forca: "80", velocidade: "60" }
}

// Recebendo os dados da View principal (HeroDetailView). Repara que "hero"
// pode ser "CharacterDTO | null" - ou seja, ou é um personagem de verdade,
// ou é null (quando ainda não carregou/deu erro). É por isso que no
// template usamos v-if/v-else-if para tratar cada situação.
defineProps<{
  loading: boolean
  errorMsg: string | null
  hero: CharacterDTO | null
  heroRank: number
}>()

// Função auxiliar para transparência (mesma lógica do StatBar.vue)
const getAlphaColor = (hex: string, alpha: string) => {
  return `${hex}${alpha}`
}
</script>

<template>
  <main class="container detail-section">
    <!-- v-if / v-else-if / v-else funcionam como um "if / else if / else"
         normal, só que decidindo qual BLOCO DE HTML deve aparecer na tela.
         Aqui, só um desses 3 blocos aparece por vez: carregando, erro, ou
         o personagem de verdade. -->
    <div v-if="loading" class="text-center loading-state loading-state-dark">
      <div class="spinner spinner-dark"></div>
      <p>Descriptografando acesso...</p>
    </div>

    <div v-else-if="errorMsg" class="error-card text-center glass-panel-dark fade-in">
      <h3>Acesso Negado</h3>
      <p>{{ errorMsg }}</p>
      <RouterLink to="/" class="btn-primary" style="display: inline-block; margin-top: 20px">
        Retornar
      </RouterLink>
    </div>

    <div v-else-if="hero" class="tactical-card glass-panel-dark fade-in">
      <div class="identity-side identity-side-dark">
        <div class="image-wrapper">
          <div
            class="tech-border tech-border-dark"
            :style="{ borderColor: getAlphaColor(hero.mainColor, '60') }"
          >
            <img :src="hero.image" :alt="hero.name" class="hero-image" />
          </div>
        </div>

        <div class="name-plate">
          <div
            class="tech-line"
            :style="{
              background: `linear-gradient(90deg, transparent, ${hero.mainColor}, transparent)`,
            }"
          ></div>
          <h1
            class="hero-name hero-name-dark"
            :style="{ textShadow: `0 2px 10px ${getAlphaColor(hero.mainColor, '40')}` }"
          >
            {{ hero.name.toUpperCase() }}
          </h1>
          <div
            class="tech-line"
            :style="{
              background: `linear-gradient(90deg, transparent, ${hero.mainColor}, transparent)`,
            }"
          ></div>
        </div>
      </div>

      <div class="data-side data-side-dark">
        <div class="data-header">
          <h2>RELATÓRIO DE STATUS</h2>
          <div
            class="badge-classified-dark"
            :style="{
              borderColor: hero.mainColor,
              color: hero.mainColor,
              backgroundColor: getAlphaColor(hero.mainColor, '15'),
            }"
          >
            RANK #{{ heroRank }}
          </div>
        </div>

        <p class="panel-desc panel-desc-dark">
          Medição de atributos táticos escaneados em tempo real.
        </p>

        <div class="stats-grid stats-grid-dark">
          <!-- v-for aqui percorre o objeto hero.powerstats (não um array!).
               Quando o v-for é usado num objeto, "(value, stat) in ..." dá
               acesso tanto ao VALOR quanto à CHAVE de cada propriedade - ou
               seja, se powerstats for { forca: "80" }, então stat = "forca"
               e value = "80". Isso gera um <StatBar> pra cada atributo que
               a API mandou, sem a gente precisar saber de antemão quais
               atributos existem. -->
          <StatBar
            v-for="(value, stat) in hero.powerstats"
            :key="stat"
            :label="stat"
            :value="value"
            :color="hero.mainColor"
          />
        </div>
      </div>
    </div>
  </main>
</template>

<style scoped>
@keyframes fadeInScale {
  0% {
    opacity: 0;
    transform: translateY(20px) scale(0.98);
  }
  100% {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

.fade-in {
  animation: fadeInScale 0.6s cubic-bezier(0.2, 0.8, 0.2, 1) forwards;
}

.container {
  max-width: 1050px;
  margin: 0 auto;
  padding: 0 20px;
  position: relative;
  z-index: 2;
}

.text-center {
  text-align: center;
}

.red-text {
  color: #dc2626;
}

.detail-section {
  padding: 50px 0 80px 0;
}

.glass-panel-dark {
  background: rgba(0, 0, 0, 0.6);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  box-shadow:
    0 25px 50px -12px rgba(0, 0, 0, 0.3),
    inset 0 0 0 1px rgba(255, 255, 255, 0.05);
}

.tactical-card {
  display: grid;
  grid-template-columns: 38% 62%; /* coluna esquerda (foto) menor que a direita (atributos) */
  border-radius: 24px;
  overflow: hidden;
}

.identity-side-dark {
  padding: 50px 30px;
  display: flex;
  flex-direction: column;
  align-items: center;
  background: linear-gradient(180deg, rgba(0, 0, 0, 0.4) 0%, rgba(10, 10, 10, 0.6) 100%);
  border-right: 1px solid rgba(255, 255, 255, 0.1);
}

.image-wrapper {
  position: relative;
  margin-bottom: 30px;
}

.tech-border-dark {
  width: 260px;
  height: 340px;
  border-radius: 12px;
  padding: 8px;
  background: #111111;
  border: 2px solid;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
}

.hero-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 6px;
  background-color: #1a1a1a;
}

.name-plate {
  width: 100%;
  text-align: center;
}

.tech-line {
  height: 2px;
  width: 100%;
  opacity: 0.5;
  margin: 8px 0;
}

.hero-name-dark {
  color: #f1f5f9;
  font-size: 2.2rem;
  font-weight: 900;
  letter-spacing: 2px;
  margin: 0;
}

.data-side-dark {
  padding: 50px;
  display: flex;
  flex-direction: column;
}

.data-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.data-header h2 {
  font-size: 1.6rem;
  font-weight: 800;
  margin: 0;
  letter-spacing: -0.5px;
  color: #f1f5f9;
}

.badge-classified-dark {
  padding: 6px 14px;
  border-radius: 4px;
  font-weight: 800;
  font-size: 0.75rem;
  letter-spacing: 1.5px;
  border: 1px solid;
}

.panel-desc-dark {
  color: #9ca3af;
  font-size: 0.95rem;
  margin-bottom: 40px;
  font-weight: 500;
}

.stats-grid-dark {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.loading-state-dark {
  padding: 100px 0;
  color: #9ca3af;
  font-weight: 600;
}

.spinner-dark {
  border: 3px solid rgba(255, 255, 255, 0.1);
  border-top: 3px solid #dc2626;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  animation: spin 1s linear infinite;
  margin: 0 auto 20px auto;
}

/* Responsividade do cartão: em telas estreitas (celular), a grade de 2
   colunas vira 1 coluna só, empilhando a foto em cima e os atributos embaixo */
@media (max-width: 850px) {
  .tactical-card {
    grid-template-columns: 1fr;
  }
  .identity-side-dark {
    border-right: none;
    border-bottom: 1px solid rgba(255, 255, 255, 0.1);
    padding: 40px 20px;
  }
  .data-side-dark {
    padding: 30px 20px;
  }
  .data-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 15px;
  }
}
</style>

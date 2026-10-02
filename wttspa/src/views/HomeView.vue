<!--
  ============================================================================
  HomeView.vue
  ----------------------------------------------------------------------------
  O CATÁLOGO de personagens (rota "/home", chamada de "Catálogo de
  Personagens" no menu). É aqui que a gente BUSCA os dados reais na API e
  mostra um <HeroCard> pra cada personagem que veio na resposta.
  ============================================================================
-->
<script setup lang="ts">
import { ref, onMounted } from 'vue'
import api from '@/services/api'
import AppNavbar from '@/components/AppNavbar.vue'
import HeroCard from '@/components/HeroCard.vue'
import StarfieldBackground from '@/components/StarfieldBackground.vue'

// Descreve o formato de cada personagem que vem da API (DTO = "Data
// Transfer Object", um jeito chique de dizer "o formato dos dados que a
// gente recebe de fora")
interface CharacterDTO {
  name: string
  image: string
  powerstats: Record<string, string>
}

// ref([]) cria uma lista reativa, começando vazia. Assim que "heroes.value"
// for preenchido com dados de verdade, o Vue re-renderiza automaticamente
// o v-for lá no template, sem a gente precisar fazer nada manual.
const heroes = ref<CharacterDTO[]>([])
const loading = ref(true)
const error = ref<string | null>(null)

// onMounted roda uma função assim que este componente aparece na tela pela
// primeira vez - é o lugar certo pra buscar dados de uma API, porque
// garante que o componente já existe antes da gente tentar mexer nele.
onMounted(async () => {
  // "async" permite usar "await" dentro da função, ou seja, "esperar" uma
  // operação demorada (como uma requisição de rede) terminar antes de
  // continuar pra próxima linha.
  try {
    error.value = null
    const response = await api.get('/api/allCharacters')
    heroes.value = response.data
  } catch (err) {
    // Se a requisição falhar por qualquer motivo (API fora do ar, erro de
    // rede, erro 401/403/500, etc.), cai aqui dentro do catch
    console.error('Erro na requisição:', err)
    error.value = 'Não foi possível carregar o arquivo de personagens.'
  } finally {
    // "finally" roda sempre, tenha dado certo ou errado - então usamos
    // isso pra garantir que o spinner de carregando sempre desapareça no
    // final, não importa o resultado.
    loading.value = false
  }
})
</script>

<template>
  <div class="page-wrapper">
    <StarfieldBackground />

    <AppNavbar />

    <section class="hero-section">
      <div class="container text-center">
        <h1 class="main-title">CATÁLoGo DE PERSoNAGENS</h1>
        <p class="sub-title">
          Explore os perfis extraídos diretamente do arquivo da galáxia.
        </p>
      </div>
    </section>

    <main class="container cards-section">
      <!-- Três estados possíveis, só um aparece por vez:
           1) ainda carregando (loading = true)
           2) deu erro (error tem uma mensagem)
           3) deu tudo certo, mostra a grade de cards -->
      <div v-if="loading" class="text-center loading-state">
        <div class="spinner"></div>
        <p>Acessando base de dados sigilosa...</p>
      </div>

      <div v-else-if="error" class="error-card text-center">
        <h3>Alerta de Segurança</h3>
        <p>{{ error }}</p>
      </div>

      <div v-else class="cards-grid">
        <!-- Pra cada personagem dentro de "heroes", cria um <HeroCard>,
             passando nome e imagem via props (heroName / heroImage).
             :key="hero.name" identifica cada card de forma única pro Vue. -->
        <HeroCard
          v-for="hero in heroes"
          :key="hero.name"
          :hero-name="hero.name"
          :hero-image="hero.image"
        />
      </div>
    </main>

    <footer class="main-footer">
      <div class="container text-center">
        <p>&copy; 2026 MackLeaps Educação. Acesso simulado para fins didáticos.</p>
      </div>
    </footer>
  </div>
</template>

<style scoped>
.page-wrapper {
  position: relative;
  font-family: 'Franklin Gothic Demi', 'Arial Narrow', sans-serif;
  background-color: #050505;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  width: 100%;
  position: relative;
  z-index: 1;
}

.text-center {
  text-align: center;
}

.hero-section {
  position: relative;
  z-index: 1;
  padding: 60px 0 20px;
}

.main-title {
  font-family: 'Star Jedi', 'Anton', sans-serif;
  font-size: clamp(2rem, 5vw, 3.2rem);
  color: #ffe81f;
  letter-spacing: 1px;
  margin-bottom: 16px;
}

.sub-title {
  font-size: 1.1rem;
  color: #cfd6dd;
  opacity: 0.8;
  max-width: 600px;
  margin: 0 auto;
}

.cards-section {
  position: relative;
  z-index: 1;
  padding: 50px 0 80px 0;
}

.cards-grid {
  display: grid;
  /* repeat(auto-fill, minmax(280px, 1fr)) é um truque de CSS Grid: cria
     quantas colunas de no mínimo 280px couberem na largura disponível,
     deixando o layout responsivo sem precisar de @media query nenhuma */
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 30px;
}

.loading-state {
  padding: 60px;
  color: #8fb4d9;
}

.spinner {
  border: 4px solid rgba(255, 232, 31, 0.15);
  border-top: 4px solid #ffe81f;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  animation: spin 1s linear infinite;
  margin: 0 auto 20px auto;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.error-card {
  background-color: rgba(188, 30, 34, 0.1);
  border: 1px solid rgba(188, 30, 34, 0.4);
  color: #f19292;
  padding: 40px;
  border-radius: 16px;
  max-width: 600px;
  margin: 0 auto;
}

.main-footer {
  position: relative;
  z-index: 1;
  padding: 30px 0;
  font-size: 0.85rem;
  color: #6b7280;
  border-top: 1px solid rgba(255, 255, 255, 0.08);
}
</style>
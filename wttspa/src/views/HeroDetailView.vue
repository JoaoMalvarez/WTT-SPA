<!--
  ============================================================================
  HeroDetailView.vue
  ----------------------------------------------------------------------------
  A página de detalhe de UM personagem específico (rota "/hero/:name"). Ela
  é responsável por buscar os dados desse personagem na API e repassar tudo
  pronto pro componente visual HeroProfile.vue desenhar na tela.

  Esta view é "burra" de propósito: ela só cuida dos DADOS (buscar, guardar
  estado de loading/erro); quem cuida do VISUAL é o HeroProfile. Essa
  separação entre "view que busca dados" e "componente que desenha" é um
  padrão comum em projetos Vue maiores.
  ============================================================================
-->
<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import api from '@/services/api'
import AppNavbar from '@/components/AppNavbar.vue'
import StarfieldBackground from '@/components/StarfieldBackground.vue'
import HeroProfile from '@/components/HeroProfile.vue'

interface CharacterDTO {
  name: string
  image: string
  mainColor: string
  powerstats: Record<string, string>
}

// useRoute() dá acesso à rota ATUAL, incluindo os parâmetros dela - nesse
// caso, o ":name" que foi definido no router/index.ts (ex: se a URL for
// /hero/yoda, então route.params.name será "yoda")
const route = useRoute()
const hero = ref<CharacterDTO | null>(null)
const loading = ref(true)
const errorMsg = ref<string | null>(null)
const heroRank = ref(0)

onMounted(async () => {
  const heroName = route.params.name

  try {
    // Usa "api" (a instância configurada em services/api.ts) em vez do
    // axios puro, montando a URL dinamicamente com o nome do personagem
    // vindo da rota.
    const response = await api.get(`/api/character/${heroName}`)
    hero.value = response.data

    // Se a API não mandou uma cor principal, usa um cinza-azulado como
    // valor padrão, pra não quebrar o visual (que depende dessa cor em
    // vários lugares).
    if (hero.value && !hero.value.mainColor) {
      hero.value.mainColor = '#475569'
    }

    // Sorteia um "rank" fictício (de 0 a 10) só pra dar um toque temático
    // de "ficha confidencial" - não vem da API.
    heroRank.value = Math.floor(Math.random() * 11)
  } catch (err) {
    console.error('Erro ao buscar detalhes:', err)
    errorMsg.value = 'Perfil corrompido ou não encontrado nos registros.'
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <div class="page-wrapper">
    <StarfieldBackground />

    <AppNavbar />

    <!-- Repassa tudo que essa view já buscou (loading, erro, dados do
         personagem, rank) pro componente visual cuidar de desenhar -->
    <HeroProfile :loading="loading" :error-msg="errorMsg" :hero="hero" :hero-rank="heroRank" />
  </div>
</template>

<style scoped>
.page-wrapper {
  position: relative;
  min-height: 100vh;
  background-color: #050505;
  font-family: 'Franklin Gothic Demi', 'Arial Narrow', sans-serif;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}
</style>
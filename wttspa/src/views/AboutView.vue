<!--
  ============================================================================
  AboutView.vue
  ----------------------------------------------------------------------------
  Página "Sobre o Projeto" (rota "/sobre"). É a página mais simples de
  todas em termos de lógica: não busca nada de API, não tem estado - é
  praticamente só texto estático organizado dentro dos componentes visuais
  que o site já tem (StarfieldBackground, AppNavbar).
  ============================================================================
-->
<script setup lang="ts">
import AppNavbar from '@/components/AppNavbar.vue'
import StarfieldBackground from '@/components/StarfieldBackground.vue'
</script>

<template>
  <div class="page-wrapper">
    <StarfieldBackground />

    <AppNavbar />

    <section class="hero-section">
      <div class="container text-center">
        <h1 class="main-title">SoBRE o PRoJETo</h1>
        <p class="sub-title">
          Uma transmissão do laboratório MackLeaps, direto da Faculdade de Computação e
          Informática.
        </p>
      </div>
    </section>

    <!-- Cada <article> é só um bloco de texto fixo, sem nenhum v-for ou
         dado dinâmico - por isso essa página não precisa de nada no
         <script> além dos imports dos componentes visuais. -->
    <main class="container content-section">
      <article class="info-block">
        <h2>O que é o Star Wars Cards?</h2>
        <p>
          Este site é um catálogo interativo de personagens de Star Wars, construído como
          exercício prático de <strong>Single Page Application (SPA)</strong> com Vue.js. Ele
          consome uma API externa para montar o arquivo de heróis, vilões e criaturas da galáxia,
          e serve como base para os alunos praticarem roteamento, consumo de dados e
          componentização.
        </p>
      </article>

      <article class="info-block">
        <h2>Quem é o MackLeaps?</h2>
        <p>
          O <strong>MackLeaps</strong> é o Laboratório de Estudos de Ambientes de Produção de
          Software da FCI (Faculdade de Computação e Informática) da Universidade Presbiteriana
          Mackenzie. O laboratório promove projetos de extensão e programas de residência de
          software, nos quais estudantes trabalham em equipes, desenvolvendo soluções reais sob 
          orientação de professores — unindo teoria e prática de mercado desde a graduação.
        </p>
      </article>

      <article class="info-block">
        <h2>Sobre este módulo:</h2>
        <p>
            Este workshop é um de uma trilha de três módulos do MackLeaps, 
            pensada pra mostrar, na prática, como uma aplicação moderna é construída 
            em camadas.
        </p>
        <p>
            Módulo 1 — SPA: Aqui o foco é o front-end: o primeiro módulo, 
            como uma Single Page Application funciona, a navegação entre páginas 
            sem recarregar o site, a organização em componentes reutilizáveis e o 
            consumo de dados vindos de uma API — tudo isso usando Vue.js e Star Wars 
            como pano de fundo pra deixar o aprendizado mais leve.
        </p>
        <p>
            Módulo 2 — BFF (Backend for Frontend): o módulo intermediário, focado em 
            construir a camada intermediária entre o front-end e os serviços de dados — 
            o backend feito sob medida pras necessidades específicas dessa interface, 
            cuidando de orquestração de chamadas, formatação de respostas e regras de negócio.
        </p>
        <p>
            Módulo 3 — Keycloak: o módulo final, dedicado à autenticação e ao controle 
            de acesso de verdade — login, gerenciamento de sessão, permissões e segurança, 
            usando o Keycloak como solução de identidade.
        </p>
        <p>
            A ideia é que, ao final dos três módulos, o aluno tenha passado por uma arquitetura completa: da tela que o usuário vê, até a camada que protege e autentica quem pode acessar o quê.
        </p>
      </article>
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
  max-width: 900px;
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
  color: #8fb4d9;
  opacity: 0.9;
  max-width: 600px;
  margin: 0 auto;
}

.content-section {
  position: relative;
  z-index: 1;
  padding: 40px 0 80px;
  display: flex;
  flex-direction: column;
  gap: 36px;
}

.info-block h2 {
  color: #ffe81f;
  font-size: 1.3rem;
  margin-bottom: 12px;
  letter-spacing: 0.5px;
}

.info-block p {
  color: #d7dde3;
  font-size: 1.05rem;
  line-height: 1.7;
  opacity: 0.9;
}

.info-block strong {
  color: #ffe81f;
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
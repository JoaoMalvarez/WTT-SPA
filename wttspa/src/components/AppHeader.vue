<!--
  ============================================================================
  AppHeader.vue
  ----------------------------------------------------------------------------
  Um cabeçalho mais simples que o AppNavbar, usado especificamente na página
  de detalhe do personagem (HeroDetailView). A diferença principal é que
  este componente usa um <slot>, um recurso do Vue que permite que quem usa
  o componente injete conteúdo customizado dentro dele.
  ============================================================================
-->
<template>
  <header class="main-header" :class="'glass-header-dark'">
    <div class="container header-content">
      <div class="logo" :class="''">Mack<span class="red-text">Leaps</span> HQ</div>
      <div>
        <!-- <slot></slot> é um "buraco" no meio do componente: tudo que for
             colocado entre <AppHeader> e </AppHeader> por quem estiver
             usando este componente (veja HeroDetailView.vue) aparece
             exatamente aqui. É assim que dá pra reaproveitar o mesmo
             cabeçalho em páginas diferentes, cada uma colocando um link
             diferente ao lado do logo. -->
        <slot></slot>
      </div>
    </div>
  </header>
</template>

<style scoped>

.main-header {
  padding: 15px 0;
  position: sticky; /* gruda no topo da tela mesmo quando a página rola */
  top: 0;
  z-index: 100;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.glass-header-dark {
  background: rgba(0, 0, 0, 0.5);
  backdrop-filter: blur(16px); /* efeito de "vidro fosco", borra o que está atrás */
  -webkit-backdrop-filter: blur(16px);
}
.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.logo {
  color: #ffffff;
  font-size: 1.5rem;
  font-weight: 800;
  letter-spacing: -1px;
}
.red-text {
  color: #dc2626;
}
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  width: 100%;
}
</style>

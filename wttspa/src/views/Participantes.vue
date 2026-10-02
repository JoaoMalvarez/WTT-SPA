<!--
  ============================================================================
  Participantes.vue
  ----------------------------------------------------------------------------
  Página que mostra um card por participantes [ rota = "/( path que você definiu no @/router/index.ts )" ]. 
  Essa página nasceu como um TODO do workshop, então os comentários aqui são
  mais detalhados que nas outras páginas - a ideia é explicar o "porquê"
  de cada decisão, não só o "o quê".
  ============================================================================
-->
<script setup lang="ts">
import AppNavbar from '@/components/AppNavbar.vue';
import StarfieldBackground from '@/components/StarfieldBackground.vue';
import { ref } from 'vue'

// ------------------------------------------------------------------------
// PARTE 1: o "molde" dos dados (a interface)
// ------------------------------------------------------------------------
// Antes de criar a lista de participantes, a gente precisa dizer ao
// TypeScript qual é o FORMATO que cada participante deve seguir. Pensa
// nisso como preencher uma ficha: toda ficha de participante TEM que ter
// RA, nome, email, cargo e comentário (são os campos "obrigatórios",
// sem "?" depois do nome). Já "imagem" e "linkedin" têm um "?" - isso
// significa que são OPCIONAIS: um participante pode existir sem
// preencher esses dois campos, e o TypeScript não vai reclamar.
interface Participante {
    RA: number;
    nome: string;
    imagem?: string;
    email: string;
    cargo: string;
    comentario: string;
    linkedin?: string;
}

// ------------------------------------------------------------------------
// PARTE 2: controlando o card "aberto" (o modal)
// ------------------------------------------------------------------------
// "ref" cria uma variável REATIVA - ou seja, sempre que o valor dela muda,
// o Vue atualiza sozinho qualquer parte da tela que dependa dela.
//
// 'participanteSelecionado' guarda: ou 'Participante' (quando um card está
// "aberto", mostrando o modal), ou "null" (quando nenhum card está aberto -
// esse é o valor inicial, por isso o modal não aparece ao carregar a página).
const participanteSelecionado = ref<Participante | null>(null)

// Essas duas funções são chamadas pelos eventos de clique lá no <template>
// (veja @click="abrirCard(pessoa)" no card, e @click="fecharCard" no
// botão de fechar / fundo escuro do modal).
function abrirCard(pessoa: Participante) {
  participanteSelecionado.value = pessoa
}

function fecharCard() {
  participanteSelecionado.value = null
}

// ------------------------------------------------------------------------
// PARTE 3: os dados de verdade - a lista de participantes
// ------------------------------------------------------------------------
// Isso é um ARRAY (lista) de objetos, e cada objeto segue exatamente o
// formato descrito na interface "Participante" lá em cima. É essa lista
// que o v-for, lá no <template>, percorre pra criar um card na tela pra
// cada pessoa - em vez de copiar/colar o HTML do card manualmente uma
// vez pra cada integrante do grupo.
const participantes: Participante[] = [
  {
    RA: 10723837,
    nome: "João Pedro Mazzante Alvarez", 
    // É recomendado usar letras minúsculas por conta da fonte do Star Wars 
    // na qual não permite algumas letras em maiúsculo
    cargo: "Copiloto da SPA",
    imagem: '/imagem/JoaoPedro.jpeg',
    email: "10723837@mackenzista.com.br",
    comentario: "Que a força Esteja com você!",
    linkedin: "https://www.linkedin.com/in/joão-pedro-mazzante-alvarez-93947537a/"
  },
  {
    RA: 10737709,
    nome: "Lívia Calado de Carvalho Dias",
    cargo: "Capitã do SPA",
    imagem: '/imagem/Libia.jpeg', 
    email: "10737709@mackenzista.com.br",
    comentario: "Eu sou seu Pai!",
    linkedin: "https://www.linkedin.com/in/lívia-calado-de-carvalho-dias-946071242/"
  },
  {
    RA: 10436997,
    nome: "Adrian Pereira da Silva Lemes",
    cargo: "Capitão do BFF",
    imagem: '/imagem/Adrian.jpeg',
    email: "10436997@mackenzista.com.br",
    comentario: "Em minha experiência, não existe sorte.",
    linkedin: "https://www.linkedin.com/in/adrian-lemes-40b734288/?isSelfProfile=true"
  },
  {
    RA: 1,
    nome: "Fulando Ciclano da Silvano",
    cargo: "Presente",
    imagem: '', // Sem foto
    email: "RA@mackenzista.com.br",
    comentario: "comentário",
    linkedin: "https://www.linkedin.com/in/seu-usuario/"
  },
  /* 
  TODO 2: coloque suas informações:
  - RA
  - nome
  - cargo que deseja ter na missão
  - imagem (caso queira) - (caso tenha duvida chame um monitor)
  - email
  - um comentário sobre o workshop!

  Dica: copie um dos blocos { ... } acima (ex: o do "Fulando"), cole
  logo abaixo dele (sempre separando cada objeto por vírgula), e troque
  os valores pelos seus. Não esqueça: "imagem" e "linkedin" são opcionais
  (podem ficar como string vazia '' se não quiser usar), mas RA, nome,
  cargo, email e comentario são obrigatórios.
  */
];

// ------------------------------------------------------------------------
// PARTE 4: avatar sorteado pra quem não colocou foto
// ------------------------------------------------------------------------
// Lista com os nomes dos arquivos que estão na pasta public/imagem/sem_pfp/
const avataresPadrao = [
  '/imagem/sem_pfp/r2d2.jpg',
  '/imagem/sem_pfp/stormtoper.jpg',
  '/imagem/sem_pfp/yodi.jpg',
  '/imagem/sem_pfp/c3p0.jpg',
  '/imagem/sem_pfp/roger.jpg'
];

// Função que retorna a imagem do usuário ou sorteia uma se estiver vazia.
//
// Repara no tipo do parâmetro: "imagemUsuario?: string" - o "?" aqui
// significa que essa função aceita ser chamada SEM nenhum argumento também
// (nesse caso, imagemUsuario seria "undefined" lá dentro).
function obterImagem(imagemUsuario?: string): string {
  // Se o usuário preencheu o campo de imagem e ele não está vazio
  if (imagemUsuario && imagemUsuario.trim() !== '') {
    return imagemUsuario;
  }
  
  // Caso contrário, escolhe uma imagem aleatória da pasta sem_pfp.
  // O "!" no final avisa o TypeScript que esse acesso ao array nunca vai
  // dar "undefined" (porque o índice sempre está dentro do tamanho da
  // lista, graças ao Math.floor e o Math.random logo acima).
  const indiceAleatorio = Math.floor(Math.random() * avataresPadrao.length);
  return avataresPadrao[indiceAleatorio]!;  
}

</script>

<template>
    <div class = "participantes">
        <StarfieldBackground />
        <AppNavbar />
        <section class = "missao">
            <h1 class = "missao-titulo">
                PARTiCiPANTES DA NoSSA MisSAo
            </h1> 
            <p class = "missao-subtitulo">
                Conheça a tripulação por trás desse projeto.
            </p>

             <!-- ==================================================================
                 GRADE DE CARDS => loop que percorre a lista de cards e coloca eles na tela
                 ------------------------------------------------------------------
                 v-for="pessoa in participantes" repete este <div class="card">
                 uma vez PRA CADA objeto dentro do array "participantes". A cada
                 repetição, "pessoa" representa um participante diferente da lista
                 (na primeira volta é o João Pedro, na segunda é a Lívia, etc.)

                 :key="pessoa.RA" - toda vez que se usa v-for, o Vue PRECISA de uma
                 forma de identificar cada item de forma única (nesse caso, usamos
                 o RA, que é um número que não se repete entre os participantes).
                 É assim que o Vue sabe "qual card é qual" se a lista mudar.

                 @click="abrirCard(pessoa)" - ao clicar em qualquer lugar do card,
                 chama a função abrirCard passando ESSA pessoa específica, o que
                 faz o modal aparecer com os dados dela.
                 ================================================================== -->
            <div class="grid-participantes">
                <div v-for="pessoa in participantes" :key="pessoa.RA" class="card" @click="abrirCard(pessoa)">
                    <!-- Usando a função para decidir se usa a foto dele ou uma aleatória -->
                    <img :src="obterImagem(pessoa.imagem)" :alt="pessoa.nome" class="card-img" />
                    
                    <div class="card-body">
                        <h3>{{ pessoa.nome }}</h3>
                        <span class="cargo">{{ pessoa.cargo }}</span>
                        <p class="email">{{ pessoa.email }}</p>
                        <!-- v-if="pessoa.linkedin" só mostra este link SE a pessoa
                             preencheu o campo linkedin (lembra que ele é opcional
                             na interface). Sem isso, apareceria um link "quebrado"
                             pra quem não colocou LinkedIn. -->
                        <a
                            v-if="pessoa.linkedin"
                            :href="pessoa.linkedin"
                            target="_blank"
                            rel="noopener noreferrer"
                            class="linkedin-link"
                            @click.stop
                        >
                            Ver LinkedIn ↗
                        </a>
                        <blockquote class="comentario">
                            "{{ pessoa.comentario }}"
                        </blockquote>
                    </div>
                </div> 
            </div><!-- fecha grid-participantes -->

            <!-- ==================================================================
                 MODAL (o card "expandido")
                 ------------------------------------------------------------------
                 v-if="participanteSelecionado" - todo esse bloco SÓ existe na tela
                 quando participanteSelecionado NÃO for null (ou seja, depois que
                 alguém clicou em algum card). Repare que dentro do modal a gente
                 usa "participanteSelecionado.nome", "participanteSelecionado.email",
                 etc. - são os dados da pessoa que foi clicada, guardados naquela
                 variável reativa lá no <script>.

                 @click="fecharCard" no fundo escuro (.modal-overlay): clicar fora
                 do card fecha o modal.

                 @click.stop no .modal-card: o ".stop" impede que um clique DENTRO
                 do card "vaze" pro elemento pai (.modal-overlay) e feche o modal
                 sem querer. Sem esse .stop, seria impossível clicar em qualquer
                 coisa dentro do modal sem ele fechar imediatamente.
                 ================================================================== -->
            <div v-if="participanteSelecionado" class="modal-overlay" @click="fecharCard">
            <div class="modal-card" @click.stop>
                <button class="modal-close" @click="fecharCard">✕</button>

                <img
                :src="obterImagem(participanteSelecionado.imagem)"
                :alt="participanteSelecionado.nome"
                class="modal-img"
                />

                <div class="modal-body">
                <h2>{{ participanteSelecionado.nome }}</h2>
                <span class="cargo">{{ participanteSelecionado.cargo }}</span>
                <p class="email">RA: {{ participanteSelecionado.RA }}</p>
                <p class="email">{{ participanteSelecionado.email }}</p>
                        <a
                            v-if="participanteSelecionado.linkedin"
                            :href="participanteSelecionado.linkedin"
                            target="_blank"
                            rel="noopener noreferrer"
                            class="linkedin-link"
                        >
                            Ver LinkedIn ↗
                        </a>
                        <blockquote class="comentario">
                            "{{ participanteSelecionado.comentario }}"
                        </blockquote>
                    </div>
                </div>
            </div>
        </section>
    </div>
</template>

<style scoped>
.participantes {
    position: relative;
    min-height: 100vh;
    background-color: #050505;
    font-family: 'Franklin Goth Demi', 'Arial Narrow', sans-serif;
    display: flex;
    flex-direction: column;
    overflow: hidden;
    color: white;
}

.missao {
    position: relative;
    z-index: 1;
    text-align: center;
    display: flex;
    flex-direction: column;
    align-items: center; /* Centraliza os itens horizontalmente */
    padding: 40px 20px;
}

.missao-titulo {
    font-family: 'Star Jedi', 'Anton', sans-serif;
    color: #ffe81f;
    font-size: clamp(2.2rem, 6vw, 4rem);
    letter-spacing: 1px;
    max-width: 900px;
    margin: 0 0 30px;
    line-height: 1.1;
}

.missao-subtitulo {
    color: #ffe81f;
    opacity: 0.85;
    font-size: 1.15rem;
    max-width: 680px;
    line-height: 1.6;
    margin: 0 0 70px;
}

.grid-participantes {
    display: flex;
    flex-wrap: wrap; /* os cards "quebram linha" sozinhos quando não cabem mais na largura */
    justify-content: center;
    gap: 30px;
    width: 100%;
    max-width: 1200px;
}

.card {
  background-color: #121212;
  border: 1px solid #333;
  border-radius: 4px;
  overflow: hidden;
  width: 280px;
  box-shadow: 0 4px 10px rgba(0,0,0,0.5);
  transition: transform 0.2s ease;
}
/* O fundo escuro que cobre a tela inteira quando o modal está aberto */
.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.8);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;  /* precisa ser bem alto pra ficar por cima de tudo, inclusive da navbar */
  padding: 20px;
}

.modal-card {
  background-color: #121212;
  border: 1px solid #333;
  border-radius: 8px;
  width: 100%;
  max-width: 420px;
  max-height: 85vh;
  overflow-y: auto; /* se o conteúdo for maior que a tela, ganha rolagem interna */
  position: relative;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.6);
}

.modal-close {
  position: absolute;
  top: 12px;
  right: 12px;
  background: rgba(0, 0, 0, 0.6);
  border: none;
  color: #fff;
  width: 32px;
  height: 32px;
  border-radius: 50%;
  cursor: pointer;
  font-size: 1rem;
  z-index: 2;
}

.modal-img {
  width: 100%;
  height: 340px;
  object-fit: cover;
}

.modal-body {
  padding: 24px;
  text-align: left;
}

.modal-body h2 {
  font-family: 'Star Jedi', 'Anton', sans-serif;
  color: #ffe81f;
  font-size: 1.5rem;
  margin: 0 0 8px;
}

.card:hover {
  transform: translateY(-5px); /* Efeito visual ao passar o mouse */
  cursor: pointer; /* Faz o mouse fazer apontar, para deixar claro que é clicavel */
}

.card-img {
  width: 100%;
  height: 250px; 
  object-fit: cover;
}

.card-body {
  padding: 16px;
  text-align: left;
}

.card-body h3 {
    font-family: 'Star Jedi', 'Anton', sans-serif;
    color: #ffe81f;
    font-size: 1.2rem;
    letter-spacing: 1px;
    max-width: 900px;
    margin: 0 0 20px;
    line-height: 1.1;
}

.card-body p {
  margin: 0;
  color: #888;
  font-size: 0.9rem;
}

.linkedin-link {
  display: inline-block;
  margin: 6px 0;
  color: #ffe81f;
  text-decoration: none;
  font-size: 0.85rem;
  font-weight: 600;
}

.linkedin-link:hover {
  text-decoration: underline;
}

</style>
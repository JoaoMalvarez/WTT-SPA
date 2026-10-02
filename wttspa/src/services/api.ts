// ==========================================================================
// services/api.ts
// --------------------------------------------------------------------------
// Este arquivo cria uma "instância" configurada do Axios (biblioteca usada
// pra fazer requisições HTTP, tipo buscar dados de uma API). Em vez de usar
// o axios "puro" em cada componente, a gente usa essa versão customizada
// (chamada aqui de "api") sempre que precisar falar com o backend.
//
// A vantagem de centralizar isso num arquivo só: se um dia precisar mudar
// alguma configuração (adicionar um token de autenticação, por exemplo),
// muda só aqui, e todos os componentes que usam "api" já recebem a mudança.
// ==========================================================================
import axios from 'axios'

// axios.create() gera uma cópia do axios com configurações próprias.
const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL ?? '',
  withCredentials: false, // não envia cookies automaticamente nas requisições
  headers: { 'Api-Key': 'mackleaps' },
})

// Um "interceptor" é uma função que roda automaticamente ANTES de você lidar
// com a resposta de uma requisição — nesse caso, o interceptor de "response"
// intercepta toda resposta que a API devolve, passe ela ou dê erro.
api.interceptors.response.use(

  // Primeira função: roda quando a requisição deu certo (sucesso)
  (response) => {
    return response // não faz nada especial, só deixa passar
  },

  // Segunda função: roda quando a requisição falhou (erro)
  (error) => {
    if (error.response) {
      // Status 403 = "Forbidden" (você está autenticado, mas não tem
      // permissão pra fazer essa ação). Quando isso acontece em qualquer
      // lugar do site, mostramos um alerta genérico automaticamente.
      if (error.response.status === 403) {
        alert('Acesso Negado: Você não tem permissão para realizar esta ação.')
      }
    }
    // Isso é importante: mesmo depois de tratar o erro aqui, a gente
    // "repassa" ele adiante (Promise.reject) pro código que chamou a
    // requisição (ex: o try/catch dentro do onMounted de uma View) também
    // ficar sabendo que deu erro.
    return Promise.reject(error)
  },
)

export default api
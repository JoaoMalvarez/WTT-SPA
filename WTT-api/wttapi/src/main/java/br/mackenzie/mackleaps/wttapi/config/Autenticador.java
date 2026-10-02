package br.mackenzie.mackleaps.wttapi.config;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class Autenticador implements AuthorizationManager<RequestAuthorizationContext> {

    private static final Logger logger = LoggerFactory.getLogger(Autenticador.class);

    @Value("${api.security.token}")
    private String apiSecretToken;

    @Override
    public AuthorizationDecision authorize(Supplier<? extends Authentication> authenticationSupplier, RequestAuthorizationContext context) {
        
        HttpServletRequest request = context.getRequest();
        String urlRequested = request.getRequestURI();
        String httpMethod = request.getMethod();

        logger.info("ACESSOU O Authentication Manager: Rota e metodo ({} {})", httpMethod, urlRequested);

        // 1. TENTA A API KEY PRIMEIRO
        String userToken = request.getHeader("Api-Key");

        if (apiSecretToken != null && apiSecretToken.equals(userToken)) {
            logger.info("Access granted via API KEY: {} {}", httpMethod, urlRequested);
            return new AuthorizationDecision(true);
        }

        // 2. FALLBACK PARA O TOKEN JWT (Keycloak) vindo do BFF
        Authentication authentication = authenticationSupplier.get();

        // Se o request tiver um Header "Authorization: Bearer ...", 
        // o Spring já validou a assinatura criptográfica antes de chegar aqui.
        // Nós só precisamos verificar se é um JwtAuthenticationToken autenticado.
        if (authentication != null && authentication.isAuthenticated() && authentication instanceof JwtAuthenticationToken jwtToken) {
            
            // Opcional: Você pode extrair dados de dentro do Token aberto se precisar!
            // String email = jwtToken.getTokenAttributes().get("email").toString();
            
            logger.info("Access granted via Keycloak JWT para o usuário: {}", jwtToken.getName());
            return new AuthorizationDecision(true);
        }
        
        logger.warn("Access denied for: {} {}", httpMethod, urlRequested);
        return new AuthorizationDecision(false);
    }
}
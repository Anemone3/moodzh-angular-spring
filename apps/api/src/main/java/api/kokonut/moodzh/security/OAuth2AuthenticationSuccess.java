package api.kokonut.moodzh.security;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import api.kokonut.moodzh.api.dto.response.TokenResponse;
import api.kokonut.moodzh.util.ApplicationProperties;
import api.kokonut.moodzh.util.CookieUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccess extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenProvider tokenProvider;
    private final ApplicationProperties properties;
    private final CookieOAuthAuthorizationRepository cookieOAuthAuthorizationRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        Optional<String> redirectUri = CookieUtils
                .getCookie(request, CookieOAuthAuthorizationRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(cookie -> cookie.getValue());

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new BadRequestException(
                    "Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }

        String targetUrlFrontend = redirectUri.orElse(getDefaultTargetUrl());

        TokenResponse token = tokenProvider.generateToken((UserPrincipal) authentication.getPrincipal(),
                properties.getExpiredToken());

        /* TODO: Enviariamos el refreshToken en las cookies */

        String targetUri = UriComponentsBuilder.fromUriString(targetUrlFrontend)
                .queryParam("token", token.getAccess_token()).build().toString();

        if (response.isCommitted()) {
            log.debug("Response has already been committed. Unable to redirect to " + targetUri);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUri);
    }

    /*
     * Limpiamos las cookies que habiamos guardado, anteriormente era para validar
     * de donde viene la
     * auth de google
     */
    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        cookieOAuthAuthorizationRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return properties.getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // solamente estoy validando el host y port, esto deja que los validate host and port, o tambien puedo dejarlo asi si deseo permitir
                    // diferentes rutas, y ya se verifica segun el port y host, ( tener en cuenta el dominio si fuera el segundo caso)
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    log.info("Authorized redirect URI: {}", authorizedURI);
                    log.info("Client URI: {}", clientRedirectUri);
                    if (authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                        log.info("host del servidor {} {} y host del cliente {} {}", authorizedURI.getHost(),
                                authorizedURI.getPort(),clientRedirectUri.getHost(),clientRedirectUri.getPort());

                        log.info("el resultado es: {}",authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                                && authorizedURI.getPort() == clientRedirectUri.getPort());

                        // TODO: aqui verificaria la ruta  de mi propierties (autohrizedUri, y el cliente)
                        // por el momento ando probando, ya en prod lo haría
                        return true;
                    }
                    return false;
                });
    }
}

package api.kokonut.moodzh.security;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.core.AuthenticationException;
import api.kokonut.moodzh.util.CookieUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class OAuth2AuthenticationFailure extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    private CookieOAuthAuthorizationRepository cookieOAuthAuthorizationRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        String targetUrl = CookieUtils
                .getCookie(request, CookieOAuthAuthorizationRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(cookie -> cookie.getValue())
                .orElse(("/"));

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", exception.getLocalizedMessage())
                .build().toUriString();

        cookieOAuthAuthorizationRepository.removeAuthorizationRequestCookies(request, response);
        log.info("Llego en AuthenticationFailure: {}", targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}

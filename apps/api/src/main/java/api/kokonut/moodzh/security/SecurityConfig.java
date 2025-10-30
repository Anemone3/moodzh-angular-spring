package api.kokonut.moodzh.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig {

    private final CustomOidcUserService oidcUserService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final OAuth2AuthenticationSuccess oAuth2AuthenticationSuccess;
    private final OAuth2AuthenticationFailure oAuth2AuthenticationFailure;

    // con este bean se encarga de verificar la informacion de los usuarios que se
    // logearan
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // bean que se encargara de encriptar las contraseñas y desencriptar
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CookieOAuthAuthorizationRepository cookieAuthorizationRequestRepository() {
        return new CookieOAuthAuthorizationRepository();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(getCorsConfig())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(getAuthorizationManagerRequestMatcherRegistryCustomizer())
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(getOAuth2LoginConfig())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler((req, res, e) -> {
                            res.sendError(HttpServletResponse.SC_FORBIDDEN);
                        }))
                .build();
    }

    private static Customizer<CorsConfigurer<HttpSecurity>> getCorsConfig() {
        return cors -> cors.configurationSource(req -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.addAllowedOrigin("*");
            corsConfiguration.addAllowedMethod("*");
            corsConfiguration.addAllowedHeader("*");
            return corsConfiguration;
        });
    }

    private Customizer<OAuth2LoginConfigurer<HttpSecurity>> getOAuth2LoginConfig() {
        return oauth2 -> oauth2
                .authorizationEndpoint(authorization -> authorization
                        .baseUri("/api/oauth2/authorization")
                        .authorizationRequestRepository(cookieAuthorizationRequestRepository()))
                .redirectionEndpoint(redirection -> redirection
                        .baseUri("/api/oauth2/callback/*")) // debe estar igual que google console, internamente spring
                                                        // maneja esta autenticacion de callback y darme un OidcUser
                .userInfoEndpoint(userinfo -> userinfo.oidcUserService(oidcUserService))
                .successHandler(oAuth2AuthenticationSuccess) // nuestro custom OAuthSuccess y Failure, para terminos de
                                                             // seguridad lo que se hace es validar
                // la request de donde viene, oseas la url y donde se va, es lo unico que se
                // hace aqui, y generar el token para el redirect con el token mismo
                .failureHandler(oAuth2AuthenticationFailure);
    }

    // manejo de endpoints
    private Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> getAuthorizationManagerRequestMatcherRegistryCustomizer() {
        return req -> req
                .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                //.requestMatchers("/api/oauth2/authorization/**")
                .anyRequest().permitAll();
    }

}

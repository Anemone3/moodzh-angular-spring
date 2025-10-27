package api.kokonut.moodzh.config;


import api.kokonut.moodzh.core.security.CustomOidcUserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOidcUserService oidcUserService;
     private final List<String> publicEndpoints = List.of(
            "/",
            "/error",
            "/api/error",
            "/api/auth/**",
            "/api/oauth2/**",
            "/api/login/oauth2/**"
            //
    );
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       return http
                .cors(cors-> cors.configurationSource(req->{
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowCredentials(true);
                    corsConfiguration.addAllowedOrigin("*");
                    return corsConfiguration;
                }))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req-> req
                        .requestMatchers(publicEndpoints.toArray(new String[0])).permitAll()
                                .anyRequest().authenticated())
               .oauth2Login(oauth2->oauth2
                       .authorizationEndpoint(authorization -> authorization
                               .baseUri("/api/oauth2/authorization"))
                       .redirectionEndpoint(redirection -> redirection
                               .baseUri("/api/login/oauth2/code/*"))
                       .userInfoEndpoint(userinfo-> userinfo.oidcUserService(oidcUserService))
                       .defaultSuccessUrl("/api/auth/ok",true)
                       .failureUrl("/api/auth/login?error"))
               .logout(logout-> logout
                       .logoutSuccessUrl("/api/auth/login")
                       .invalidateHttpSession(true)
                       .clearAuthentication(true)
                       .deleteCookies("JSESSIONID")
                       .permitAll())
               .exceptionHandling(ex -> ex
                       .authenticationEntryPoint((req, res, e) -> {
                           res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                       })
                       .accessDeniedHandler((req, res, e) -> {
                           res.sendError(HttpServletResponse.SC_FORBIDDEN);
                       }))
               .build();
    }
}

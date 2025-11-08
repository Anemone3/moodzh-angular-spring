package api.kokonut.moodzh.core.services.auth;

import java.time.Instant;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import api.kokonut.moodzh.api.dto.request.LoginRequest;
import api.kokonut.moodzh.api.dto.request.RegisterRequest;
import api.kokonut.moodzh.api.dto.response.TokenResponse;
import api.kokonut.moodzh.api.dto.response.UserResponse;
import api.kokonut.moodzh.api.exceptions.auth.AuthUserExists;
import api.kokonut.moodzh.api.exceptions.http.ResourceNotFoundException;
import api.kokonut.moodzh.data.enums.ProviderAuth;
import api.kokonut.moodzh.data.model.User;
import api.kokonut.moodzh.data.repository.UserRepository;
import api.kokonut.moodzh.security.JwtTokenProvider;
import api.kokonut.moodzh.security.UserPrincipal;
import api.kokonut.moodzh.util.ApplicationProperties;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

<<<<<<< HEAD
=======
import java.time.Instant;

import java.util.Optional;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

>>>>>>> c89c24f851212723bc45ff0d8456d34e2aaf678a
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider tokenProvider;
    private final ApplicationProperties properties;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    // TODO: falta el refreshToken, esto retorna resfreshTOken null

    @Override
    public TokenResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new AuthUserExists("Ya existe un correo registrado");
        }

        User newUser = User.builder()
                .email(request.email())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .isActive(true)
                .provider(ProviderAuth.LOCAL)
                .providerId(null)
                .build();

        User persistedUser = userRepository.save(newUser);

        UserPrincipal userPrincipal = new UserPrincipal(persistedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userPrincipal,
                null,
                userPrincipal.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.generateToken(userPrincipal, properties.getExpiredToken());
    }

    @Override
    public TokenResponse authenticateLocalUser(LoginRequest request, HttpServletResponse response) {
        // usamos el authenticationManager para que BCrypt se encargue de manejar la
        // passowrd desecodearla
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()));
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        return tokenProvider.generateToken(principal, properties.getExpiredToken());
    }

    @Override
    public TokenResponse generateTokensForOAuth2User(UserPrincipal principal, HttpServletResponse response) {
        long millis = properties.getExpiredToken();
        return tokenProvider.generateToken(principal, millis);
    }

    @Override
    public TokenResponse refreshAccessToken(String refreshTokenValue, HttpServletResponse response) {
        return null;
    }

    @Override
    public UserResponse getCurrentUser(@AuthenticationPrincipal UserPrincipal principal) {
        Optional<User> userExists = userRepository.findByEmail(principal.getEmail());
        if (!userExists.isEmpty()) {
            User user = userExists.get();

<<<<<<< HEAD
            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd-MM-yyyy
            // HH:mm:ss")
            // .withZone(ZoneId.systemDefault());
            Instant createtAt = user.getCreatedAt();
            Instant updatedAt = user.getUpdatedAt();

            // String formatedcreatetAt = formatter.format(createtAt);
            // String formatedupdated = formatter.format(updatedAt);
=======
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd-MM-yyyy HH:mm:ss").withZone(ZoneId.systemDefault());
            Instant createtAt = user.getCreatedAt();
            Instant updatedAt = user.getUpdatedAt();

            //String formatedcreatetAt = formatter.format(createtAt);
            //String formatedupdated = formatter.format(updatedAt);
>>>>>>> c89c24f851212723bc45ff0d8456d34e2aaf678a

            return UserResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .profile(user.getProfilePicture())
                    .socialLinks(user.getSocialLinks())
                    .location(user.getLocation())
                    .providerId(user.getProviderId())
                    .provider(user.getProvider().name())
                    .createdAt(createtAt.toString())
                    .lastUpdated(updatedAt.toString())
                    .build();
        }

        throw new ResourceNotFoundException("Hubo un problema en la authenticacion, no se encontró el usuario");
    }

    @Override
    public void logout(String refreshTokenValue) {
    }
}

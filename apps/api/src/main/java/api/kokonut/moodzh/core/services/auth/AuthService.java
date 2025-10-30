package api.kokonut.moodzh.core.services.auth;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import api.kokonut.moodzh.api.dto.request.LoginRequest;
import api.kokonut.moodzh.api.dto.request.RegisterRequest;
import api.kokonut.moodzh.api.dto.response.TokenResponse;
import api.kokonut.moodzh.api.dto.response.UserResponse;
import api.kokonut.moodzh.security.UserPrincipal;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    TokenResponse register(RegisterRequest request);

    TokenResponse authenticateLocalUser(LoginRequest request, HttpServletResponse response);

    TokenResponse generateTokensForOAuth2User(UserPrincipal principal, HttpServletResponse response);

    TokenResponse refreshAccessToken(String refreshTokenValue, HttpServletResponse response);

    UserResponse getCurrentUser(@AuthenticationPrincipal UserPrincipal principal);

    void logout(String refreshTokenValue);
}

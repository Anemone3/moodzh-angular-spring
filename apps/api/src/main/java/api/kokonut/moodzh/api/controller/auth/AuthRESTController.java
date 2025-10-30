package api.kokonut.moodzh.api.controller.auth;

import api.kokonut.moodzh.api.dto.request.LoginRequest;
import api.kokonut.moodzh.api.dto.request.RegisterRequest;
import api.kokonut.moodzh.api.dto.response.TokenResponse;
import api.kokonut.moodzh.api.dto.response.UserResponse;
import api.kokonut.moodzh.core.services.auth.AuthService;
import api.kokonut.moodzh.security.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRESTController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse req) {
        return ResponseEntity.ok(authService.authenticateLocalUser(loginRequest, req));
    }

    @GetMapping("/token")
    public String refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return new String();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(authService.getCurrentUser(user));
    }
}

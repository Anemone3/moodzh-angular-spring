package api.kokonut.moodzh.api.controller.auth;

import api.kokonut.moodzh.api.dto.request.LoginRequest;
import api.kokonut.moodzh.api.dto.request.RegisterRequest;
import api.kokonut.moodzh.api.dto.response.TokenResponse;
import api.kokonut.moodzh.api.dto.response.UserResponse;
import api.kokonut.moodzh.core.services.auth.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRESTController {


    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            return ResponseEntity.ok(authService.register(registerRequest));
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse req) {
        return ResponseEntity.ok(authService.authenticateLocalUser(loginRequest,req));
    }

    @GetMapping("/ok")
    public ResponseEntity<?> me(@AuthenticationPrincipal UserDetails user){
        Map<String, String> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("password", user.getPassword());
        map.put("role", user.getAuthorities().iterator().next().getAuthority());
        return ResponseEntity.ok(map);
    }
}

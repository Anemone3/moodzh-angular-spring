package api.kokonut.moodzh.api.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthRESTController {


    @GetMapping("/success")
    public String ok(){
        return "Success";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
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

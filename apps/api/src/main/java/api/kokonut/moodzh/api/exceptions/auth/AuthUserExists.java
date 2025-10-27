package api.kokonut.moodzh.api.exceptions.auth;

public class AuthUserExists extends RuntimeException {
    public AuthUserExists(String message) {
        super(message);
    }
}

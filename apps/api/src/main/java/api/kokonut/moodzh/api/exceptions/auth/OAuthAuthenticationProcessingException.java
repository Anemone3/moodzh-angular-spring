package api.kokonut.moodzh.api.exceptions.auth;

import org.springframework.security.core.AuthenticationException;
;

public class OAuthAuthenticationProcessingException extends AuthenticationException {
    public OAuthAuthenticationProcessingException(String message,Throwable cause) {
        super(message,cause);
    }

    public OAuthAuthenticationProcessingException(String msg) {
        super(msg);
    }
}

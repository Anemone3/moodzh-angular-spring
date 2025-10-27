package api.kokonut.moodzh.core.strategy.oauth.models;

import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public abstract class AbstractOAuth2UserInfo implements OAuth2User {
    protected Map<String, Object> attributes;

    public AbstractOAuth2UserInfo(OidcUser oidcUser) {
        this.attributes = oidcUser.getAttributes();
    }

    public abstract String getId();
    public abstract String providerId();
    public abstract String getName();
    public abstract String getEmail();
    public abstract String getImageUrl();


    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public OidcIdToken getIdToken() {
        return null;
    }

    public OidcUserInfo getUserInfo() {
        return null;
    }
}

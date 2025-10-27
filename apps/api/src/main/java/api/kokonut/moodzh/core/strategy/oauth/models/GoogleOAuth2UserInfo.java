package api.kokonut.moodzh.core.strategy.oauth.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collection;

public class GoogleOAuth2UserInfo extends AbstractOAuth2UserInfo{

    private final OidcIdToken idToken;
    private final OidcUserInfo userInfo;

    public GoogleOAuth2UserInfo(OidcUser oidcUser) {
        super(oidcUser);
        this.idToken = oidcUser.getIdToken();
        this.userInfo = oidcUser.getUserInfo();
    }
    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String providerId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
    }

    @Override
    public OidcIdToken getIdToken() {
        return idToken;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return userInfo;
    }

}

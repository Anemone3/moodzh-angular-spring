package api.kokonut.moodzh.security;

import api.kokonut.moodzh.core.strategy.oauth.models.AbstractOAuth2UserInfo;
import api.kokonut.moodzh.data.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
public class UserPrincipal implements OidcUser,UserDetails {

    private final String id;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    private final Map<String, Object> attributes;
    private final OidcIdToken idToken;
    private final OidcUserInfo userInfo;

    // para user locales
    public UserPrincipal(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.authorities = getAuthoritiesFromUser(user);
        this.password = user.getPassword();
        this.attributes = Collections.emptyMap();
        this.idToken = null;
        this.userInfo = null;
    }

    // para users de providers ( google, github ,etc )
    public UserPrincipal(User user, AbstractOAuth2UserInfo userInfoStrategy) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.authorities = getAuthoritiesFromUser(user);

        this.password = null;
        this.attributes = userInfoStrategy.getAttributes();
        this.idToken = userInfoStrategy.getIdToken();
        this.userInfo = userInfoStrategy.getUserInfo();
    }

    @Override public Map<String, Object> getClaims() { return (idToken != null) ? idToken.getClaims() : Collections.emptyMap(); }
    @Override public OidcUserInfo getUserInfo() { return userInfo; }
    @Override public OidcIdToken getIdToken() { return idToken; }
    @Override public Map<String, Object> getAttributes() { return (attributes != null) ? attributes : Collections.emptyMap(); }
    @Override public String getName() {
        return (attributes != null && attributes.containsKey("sub")) ? (String) attributes.get("sub") : this.id;
    }


    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override public String getUsername() { return this.email; }
    @Override public String getPassword() { return this.password; }
    @Override public boolean isAccountNonExpired() { return UserDetails.super.isAccountNonExpired(); }
    @Override public boolean isAccountNonLocked() { return UserDetails.super.isAccountNonLocked(); }
    @Override public boolean isCredentialsNonExpired() { return UserDetails.super.isCredentialsNonExpired(); }
    @Override public boolean isEnabled() { return UserDetails.super.isEnabled(); }


    private static Collection<? extends GrantedAuthority> getAuthoritiesFromUser(User user) {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
    }


}

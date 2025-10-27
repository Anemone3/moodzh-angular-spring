package api.kokonut.moodzh.security;


import api.kokonut.moodzh.core.services.user.UserService;
import api.kokonut.moodzh.core.strategy.oauth.IOAuthStrategy;
import api.kokonut.moodzh.core.strategy.oauth.OAuthProviderFactory;
import api.kokonut.moodzh.data.enums.ProviderAuth;
import api.kokonut.moodzh.data.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CustomOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final OidcUserService delegate;
    private final OAuthProviderFactory oAuthStrategy;
    private final UserService userService;
    public CustomOidcUserService(OAuthProviderFactory oAuthStrategy, UserService userService) {
        this.delegate = new OidcUserService();
        this.oAuthStrategy = oAuthStrategy;
        this.userService = userService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        return processOAuthWithFactory(userRequest);
    }

    private OidcUser processOAuthWithFactory(OidcUserRequest userRequest){
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("registration id {}", registrationId);
        OidcUser oidcUser = delegate.loadUser(userRequest);
        log.info("oidc user {}", oidcUser);
        IOAuthStrategy strategy = oAuthStrategy.getStrategy(registrationId);
        User user = strategy.executeUpsert(oidcUser);
        log.info("user proccess Auth factory{}", user);
        return new UserPrincipal(user, strategy.getUserInfo(oidcUser));
    }


    private OidcUser  proccesWithOidcRequests(OidcUserRequest userRequest) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("registration id {}", registrationId);
        OidcUser oidcUser = delegate.loadUser(userRequest);
        //ProviderAuth.valueOf(registrationId)
        User user = userService.oAuthFindEmailOrCreate(oidcUser, ProviderAuth.valueOf(registrationId.toUpperCase()));
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
        log.info("user in DB : {}", user);
        return new DefaultOidcUser(authorities,oidcUser.getIdToken(),oidcUser.getUserInfo());
    }


}

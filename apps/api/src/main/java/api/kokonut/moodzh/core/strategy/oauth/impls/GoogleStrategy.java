package api.kokonut.moodzh.core.strategy.oauth.impls;

import api.kokonut.moodzh.core.services.user.UserService;
import api.kokonut.moodzh.core.strategy.oauth.models.AbstractOAuth2UserInfo;
import api.kokonut.moodzh.core.strategy.oauth.IOAuthStrategy;
import api.kokonut.moodzh.core.strategy.oauth.models.GoogleOAuth2UserInfo;
import api.kokonut.moodzh.data.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import static api.kokonut.moodzh.data.enums.ProviderAuth.*;

@Component("google")
@AllArgsConstructor
public class GoogleStrategy implements IOAuthStrategy {

    private final UserService userService;

    @Override
    public AbstractOAuth2UserInfo getUserInfo(OAuth2User oAuth2User) {
        return new GoogleOAuth2UserInfo((OidcUser) oAuth2User);
    }

    @Override
    public User executeUpsert(OAuth2User oAuth2User) {
        AbstractOAuth2UserInfo userInfo = getUserInfo(oAuth2User);
        return userService.oAuthFindEmailOrCreate(userInfo,GOOGLE);
    }

}

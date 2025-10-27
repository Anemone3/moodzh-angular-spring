package api.kokonut.moodzh.core.strategy.oauth;

import api.kokonut.moodzh.core.strategy.oauth.models.AbstractOAuth2UserInfo;
import api.kokonut.moodzh.data.model.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface IOAuthStrategy {


    User executeUpsert(OAuth2User oAuth2User);

    AbstractOAuth2UserInfo getUserInfo(OAuth2User oAuth2User);
}

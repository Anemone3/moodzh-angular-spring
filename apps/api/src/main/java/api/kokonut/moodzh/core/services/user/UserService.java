package api.kokonut.moodzh.core.services.user;

import api.kokonut.moodzh.api.dto.request.RegisterRequest;
import api.kokonut.moodzh.api.dto.request.UserOAuthRequest;
import api.kokonut.moodzh.api.dto.response.UserResponse;
import api.kokonut.moodzh.api.exceptions.auth.AuthUserExists;
import api.kokonut.moodzh.api.exceptions.http.ResourceNotFoundException;
import api.kokonut.moodzh.core.strategy.oauth.models.AbstractOAuth2UserInfo;
import api.kokonut.moodzh.data.enums.ProviderAuth;
import api.kokonut.moodzh.data.model.User;
import api.kokonut.moodzh.data.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User oAuthFindEmailOrCreate(AbstractOAuth2UserInfo userInfo, ProviderAuth providerAuth) {
        return userRepository.findByEmail(userInfo.getEmail()).orElseGet(()->{
            var newUser = UserOAuthRequest.builder()
                    .email(userInfo.getEmail())
                    .password(null)
                    .profileUrl(userInfo.getImageUrl())
                    .username(userInfo.getName())
                    .provider(providerAuth.name())
                    .providerId(userInfo.providerId())
                    .build();

            return createUserFromOAuth(newUser);
        });
    }

    public User oAuthFindEmailOrCreate(OidcUser oidcUser,ProviderAuth providerAuth) {
        String name = oidcUser.getAttribute("name");
        String email = oidcUser.getAttribute("email");
        String profileUrl = oidcUser.getAttribute("picture");
        String providerId = oidcUser.getAttribute("sub");
        return userRepository.findByEmail(email).orElseGet(()-> {
            var newUser = UserOAuthRequest.builder()
                    .username(name)
                    .email(email)
                    .password(null)
                    .profileUrl(profileUrl)
                    .providerId(providerId)
                    .provider(providerAuth.name())
                    .build();
            return createUserFromOAuth(newUser);
        });
    }


    public User fetchUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found"));
    }



    public User createUserFromOAuth(UserOAuthRequest user){
        var newUser = User.builder()
                .username(user.username())
                .email(user.email())
                .password(user.password())
                .profilePicture(user.profileUrl())
                .provider(ProviderAuth.valueOf(user.provider().toUpperCase()))
                .isActive(Boolean.TRUE)
                .providerId(user.providerId())
                .build();

        return userRepository.save(newUser);
    }
}

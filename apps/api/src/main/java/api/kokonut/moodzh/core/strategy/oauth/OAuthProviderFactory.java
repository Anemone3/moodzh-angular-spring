package api.kokonut.moodzh.core.strategy.oauth;


import api.kokonut.moodzh.api.exceptions.auth.OAuthAuthenticationProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;


@Component
@AllArgsConstructor
public class OAuthProviderFactory {
    private final Map<String,IOAuthStrategy> oAuthStrategyMap;

    public IOAuthStrategy getStrategy(String strategyId) {
        IOAuthStrategy strategy = oAuthStrategyMap.get(strategyId);
        if(Objects.isNull(strategy)) {
            throw new OAuthAuthenticationProcessingException("Unknown OAuth strategy: " + strategyId);
        }
        return strategy;
    }

}


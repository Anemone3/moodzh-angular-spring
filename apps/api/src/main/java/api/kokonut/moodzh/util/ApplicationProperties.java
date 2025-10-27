package api.kokonut.moodzh.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {
    private List<String> authorizedRedirectUris = new ArrayList<>();

    private String secretToken;

    private long expiredRefreshToken;
    private long expiredToken;
}

package nsu.fit.config.gigachat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("gigachat.auth")
public class GigaChatAuthProperties {
    private String key;

    private String clientId;

    private String clientSecret;
}

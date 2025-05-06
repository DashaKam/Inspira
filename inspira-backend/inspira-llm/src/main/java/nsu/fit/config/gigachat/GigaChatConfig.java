package nsu.fit.config.gigachat;

import chat.giga.client.GigaChatClient;
import chat.giga.client.auth.AuthClient;
import chat.giga.client.auth.AuthClientBuilder;
import chat.giga.model.Scope;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GigaChatAuthProperties.class)
public class GigaChatConfig {

    @Bean
    public GigaChatClient gigaChatClient(GigaChatAuthProperties authProperties) {
        return GigaChatClient.builder()
                .verifySslCerts(false)
                .authClient(AuthClient.builder()
                        .withOAuth(AuthClientBuilder.OAuthBuilder.builder()
                                .scope(Scope.GIGACHAT_API_PERS)
                                .authKey(authProperties.getKey())
                                .clientId(authProperties.getClientId())
                                .clientSecret(authProperties.getClientSecret())
                                .build())
                        .build())
                .build();
    }

}

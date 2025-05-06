package nsu.fit.config;

import jakarta.validation.MessageInterpolator;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfig {

    private static final String DEFAULT_VALIDATION_MESSAGES_RESOURCE_BUNDLE = "ValidationMessagesResourceBundle";

    @Bean
    public Validator validatorWithMessages(MessageInterpolator messageInterpolator) {
        return Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(messageInterpolator)
                .buildValidatorFactory()
                .getValidator();
    }

    @Bean
    public MessageInterpolator defaultValidationsMessageInterpolator() {
        return new ResourceBundleMessageInterpolator(
                new PlatformResourceBundleLocator(DEFAULT_VALIDATION_MESSAGES_RESOURCE_BUNDLE)
        );
    }
}

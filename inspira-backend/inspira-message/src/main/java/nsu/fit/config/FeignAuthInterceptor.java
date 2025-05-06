package nsu.fit.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import nsu.fit.config.ContextProvider;
import nsu.fit.web.headers.CustomHttpHeader;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeignAuthInterceptor implements RequestInterceptor {

    private final ContextProvider contextProvider;

    @Override
    public void apply(RequestTemplate template) {
        Integer userId = contextProvider.getUserId();

        if (userId != null) {
            template.header(CustomHttpHeader.USER_ID.getName(), String.valueOf(userId));
        }
    }
}

package nsu.fit.web.context;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import nsu.fit.config.ContextProvider;
import nsu.fit.web.headers.CustomHttpHeader;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@RequiredArgsConstructor
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestContextProvider implements ContextProvider {

    private final HttpServletRequest httpServletRequest;

    @Override
    public Integer getUserId() {
        return Integer.parseInt(httpServletRequest.getHeader(CustomHttpHeader.USER_ID.getName()));
    }

}


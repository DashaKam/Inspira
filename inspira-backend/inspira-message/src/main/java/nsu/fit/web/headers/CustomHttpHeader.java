package nsu.fit.web.headers;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomHttpHeader {

    USER_ID("X-User-Id");

    private final String name;
}


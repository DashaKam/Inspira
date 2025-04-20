package nsu.fit.util;

import lombok.experimental.UtilityClass;
import nsu.fit.domain.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class SecurityUtil {
    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

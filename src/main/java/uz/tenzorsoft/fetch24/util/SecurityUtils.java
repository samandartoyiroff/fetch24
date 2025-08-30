package uz.tenzorsoft.fetch24.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.tenzorsoft.fetch24.domain.User;
import java.util.Optional;

public class SecurityUtils {

    public static Optional<Long> getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return Optional.empty();
        }
        if (authentication.getPrincipal() instanceof User) {
            return Optional.of(((User) authentication.getPrincipal()).getId());
        }
        return Optional.empty();
    }
}
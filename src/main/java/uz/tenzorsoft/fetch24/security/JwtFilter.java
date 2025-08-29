package uz.tenzorsoft.fetch24.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.tenzorsoft.fetch24.domain.User;
import uz.tenzorsoft.fetch24.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        String authorizationToken = request.getHeader("Authorization");

        if (authorizationToken != null) {
            String token = authorizationToken.trim(); // tokenni tozalab olish
            try {
                if (jwtUtil.isValid(token)) {
                    String username = jwtUtil.getUsername(token);
                    Optional<User> optionalAdmin = userRepository.findByUsername(username);
                    if (optionalAdmin.isPresent()) {
                        User user = optionalAdmin.get();
                        var auth = new UsernamePasswordAuthenticationToken(user, null, Set.of(user.getRole()));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    } else {
                        System.err.println("Admin topilmadi: " + username);
                    }
                } else {
                    System.err.println("Token yaroqsiz: " + token);
                }
            } catch (Exception e) {
                System.err.println("JWT tokenni o‘qishda xatolik: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}

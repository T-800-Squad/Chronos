package moduloGestionUsuarios.UserManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for Spring Security.
 * This class sets up the security filter chain to:
 * - Disable CSRF protection (suitable for stateless REST APIs).
 * - Set session management to stateless, indicating that the application does not use HTTP sessions.
 * - Permit all incoming requests without requiring authentication (this is usually for development purposes).
 * - Add a custom JWT filter before the default username/password authentication filter.
 * Also provides a bean for password encoding using BCrypt.
 */
@Configuration
public class SecurityConfig {

    /**
     * Configures the HTTP security for the application.
     *
     * @param http the {@link HttpSecurity} object to configure
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }

    /**
     * Provides a BCrypt password encoder bean.
     *
     * @return a {@link PasswordEncoder} instance using BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

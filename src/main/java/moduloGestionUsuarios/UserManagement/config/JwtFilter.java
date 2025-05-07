package moduloGestionUsuarios.UserManagement.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    private final String SECRET_KEY = "ContraseñaSuperSecreta123";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String path = request.getRequestURI();
        if (path.equals("/authentication/login")) {
            chain.doFilter(request, response);
            return;
        }

        // Si no hay token o no empieza con "Bearer ", rechazar la petición
        if (header == null || !header.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session error, no token");
            return;
        }

        String token = header.substring(7); // Eliminar "Bearer "
        try {
            JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
        } catch (JWTVerificationException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Tu sesion ya expiro");
        }

        chain.doFilter(request, response);
    }
}

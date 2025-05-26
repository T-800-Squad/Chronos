package moduloGestionUsuarios.UserManagement.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class JwtFilterTest {

    private JwtFilter jwtFilter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;

    @BeforeEach
    void setup() {
        jwtFilter = new JwtFilter();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
    }

    @Test
    void shouldAllowSwaggerPathWithoutToken() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/swagger-ui/index.html");
        when(request.getMethod()).thenReturn("GET");

        jwtFilter.doFilterInternal(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
        verify(response, never()).sendError(anyInt(), anyString());
    }

    @Test
    void shouldRejectWhenNoTokenProvided() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/user");
        when(request.getHeader("Authorization")).thenReturn(null);
        when(request.getMethod()).thenReturn("POST");

        jwtFilter.doFilterInternal(request, response, chain);

        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session error, no token");
        verify(chain, never()).doFilter(request, response);
    }


    @Test
    void shouldAllowIfTokenIsValidAndRoleIsExtracurricularTeacherAndPathIsUserQuery() throws ServletException, IOException {
        String token = JWT.create()
                .withClaim("role", "EXTRACURRICULAR_TEACHER")
                .sign(Algorithm.HMAC256("supersecretpassword1234567891011121314"));

        when(request.getRequestURI()).thenReturn("/user/query");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(request.getMethod()).thenReturn("GET");

        jwtFilter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(response, never()).sendError(anyInt(), anyString());
    }

    @Test
    void shouldRejectIfRoleIsNotAdminAndPathIsRestricted() throws ServletException, IOException {
        String token = JWT.create()
                .withClaim("role", "STUDENT")
                .sign(Algorithm.HMAC256("supersecretpassword1234567891011121314"));

        when(request.getRequestURI()).thenReturn("/restricted");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(request.getMethod()).thenReturn("GET");

        jwtFilter.doFilterInternal(request, response, chain);

        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session error, no token");
        verify(chain, never()).doFilter(request, response);
    }

    @Test
    void shouldAllowIfAdminTokenAndAnyPath() throws ServletException, IOException {
        String token = JWT.create()
                .withClaim("role", "ADMIN")
                .sign(Algorithm.HMAC256("supersecretpassword1234567891011121314"));

        when(request.getRequestURI()).thenReturn("/admin/endpoint");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(request.getMethod()).thenReturn("GET");

        jwtFilter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(response, never()).sendError(anyInt(), anyString());
    }

    @Test
    void shouldAllowOptionsRequest() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("OPTIONS");

        jwtFilter.doFilterInternal(request, response, chain);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(chain, never()).doFilter(any(), any());
    }
}

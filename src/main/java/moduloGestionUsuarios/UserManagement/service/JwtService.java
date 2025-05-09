package moduloGestionUsuarios.UserManagement.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import moduloGestionUsuarios.UserManagement.model.Role;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private final String SECRET_KEY = "ContraseñaSuperSecreta123";
    private long expirationTime;

    public String generateToken(String id,String userName, String email, String name, Role role) {
        expirationTime = expirationTime(role);
        return JWT.create()
                .withClaim("id", id)
                .withClaim("userName",userName)
                .withClaim("email",email)
                .withClaim("name", name)
                .withClaim("role", role.getDescription())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    private long expirationTime(Role role) {
        if (role.equals(Role.MEDICAL_SECRETARY)) {
            return 1000*60*60*8;
        }
        return 1000*60*60;
    }

    public String getId(String token) {
        token = token.substring(7);
        return JWT.decode(token).getClaim("id").asString();
    }

    public String getUserName(String token) {
        token = token.substring(7);
        return JWT.decode(token).getClaim("userName").asString();
    }

    public String getRole(String token) {
        token = token.substring(7);
        return JWT.decode(token).getClaim("role").asString();
    }

    public String getEmail(String token) {
        token = token.substring(7);
        return JWT.decode(token).getClaim("email").asString();
    }

    public String getName(String token) {
        token = token.substring(7);
        return JWT.decode(token).getClaim("name").asString();
    }

}

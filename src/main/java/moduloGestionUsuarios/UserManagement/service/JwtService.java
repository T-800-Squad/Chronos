package moduloGestionUsuarios.UserManagement.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private final String SECRET_KEY = "ContraseñaSuperSecreta123";
    private long expirationTime;

    public String generateToken(String userName,String role) {
        expirationTime = expirationTime(role);
        return JWT.create()
                .withClaim("userName",userName)
                .withClaim("role", role)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    private long expirationTime(String role) {
        if (role.equals("student")) {
            return 1000*60*60;
        }
        return 1000*60*60*12;
    }

    public String getUserName(String token) {
        return JWT.decode(token).getClaim("userName").asString();
    }

    public String getRole(String token) {
        return JWT.decode(token).getClaim("role").asString();
    }



}

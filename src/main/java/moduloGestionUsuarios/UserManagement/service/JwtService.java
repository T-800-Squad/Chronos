package moduloGestionUsuarios.UserManagement.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import moduloGestionUsuarios.UserManagement.model.Role;
import moduloGestionUsuarios.UserManagement.model.Specialty;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service responsible for generating and parsing JWT.
 * This service encodes user identity and role information into secure tokens that expire after a defined period.
 */
@Service
public class JwtService {
    private final String SECRET_KEY = "supersecretpassword1234567891011121314";
    private long expirationTime;

    /**
     * Generates a signed JWT token containing user information and role.
     *
     * @param id       Unique identifier of the user.
     * @param userName Username of the user.
     * @param email    Email address of the user.
     * @param name     Full name of the user.
     * @param role     Role assigned to the user.
     * @return A signed JWT token containing the provided user claims.
     */
    public String generateToken(String id, String userName, String email, String name, String role, String specialty) {
        expirationTime = expirationTime(role);

        return JWT.create()
                .withClaim("id", id)
                .withClaim("userName",userName)
                .withClaim("email",email)
                .withClaim("name", name)
                .withClaim("role", role)
                .withClaim("specialty",specialty)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    /**
     * Determines the token expiration time based on the user's role.
     *
     * @param role The user's role.
     * @return Expiration time in milliseconds.
     */
    private long expirationTime(String role) {
        Role roleEnum = Role.valueOf(role);
        if (roleEnum.equals(Role.MEDICAL_SECRETARY)) {
            return 1000*60*60*8;
        }
        return 1000*60*60;
    }

    /**
     * Extracts the user ID from a JWT token.
     *
     * @param token A JWT token (usually prefixed with "Bearer ").
     * @return The user ID claim.
     */
    public String getId(String token) {
        token = token.substring(7);
        return JWT.decode(token).getClaim("id").asString();
    }

    /**
     * Extracts the username from a JWT token.
     *
     * @param token A JWT token.
     * @return The username claim.
     */
    public String getUserName(String token) {
        token = token.substring(7);
        return JWT.decode(token).getClaim("userName").asString();
    }

    /**
     * Extracts the role from a JWT token.
     *
     * @param token A JWT token.
     * @return The role claim.
     */
    public String getRole(String token) {
        token = token.substring(7);
        return JWT.decode(token).getClaim("role").asString();
    }

    /**
     * Extracts the email address from a JWT token.
     *
     * @param token A JWT token.
     * @return The email claim.
     */
    public String getEmail(String token) {
        token = token.substring(7);
        return JWT.decode(token).getClaim("email").asString();
    }

    /**
     * Extracts the full name from a JWT token.
     *
     * @param token A JWT token.
     * @return The name claim.
     */
    public String getName(String token) {
        token = token.substring(7);
        return JWT.decode(token).getClaim("name").asString();
    }

}

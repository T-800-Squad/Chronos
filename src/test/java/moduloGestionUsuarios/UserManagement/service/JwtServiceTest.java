package moduloGestionUsuarios.UserManagement.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JwtServiceTest {
    @Autowired
    private JwtService jwtService;

    @Test
    public void testJwtTokenGenerateToStudent() {
        String token = jwtService.generateToken("test","student");
        assertEquals("test",jwtService.getUserName(token));
        assertEquals("student",jwtService.getRole(token));
    }
    @Test
    public void testJwtTokenGenerateToAnotherRole() {
        String token = jwtService.generateToken("test","administrator");
        assertEquals("administrator",jwtService.getRole(token));
        assertEquals("test",jwtService.getUserName(token));
    }

}

package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.model.Role;
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
        String token = jwtService.generateToken("test","test","test","test", Role.STUDENT);
        token = "Bearer " + token;
        assertEquals("test",jwtService.getUserName(token));
        assertEquals("Student",jwtService.getRole(token));
        assertEquals("test",jwtService.getEmail(token));
        assertEquals("test",jwtService.getId(token));
        assertEquals("test",jwtService.getName(token));
    }
    @Test
    public void testJwtTokenGenerateToAnotherRole() {
        String token = jwtService.generateToken("test","test","test","test",Role.ADMIN);
        token = "Bearer " + token;
        assertEquals("Administrator",jwtService.getRole(token));
        assertEquals("test",jwtService.getUserName(token));
        assertEquals("test",jwtService.getEmail(token));
        assertEquals("test",jwtService.getId(token));
        assertEquals("test",jwtService.getName(token));
    }

}

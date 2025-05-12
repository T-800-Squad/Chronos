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
        String token = jwtService.generateToken("test","test","test","test",Role.MEDICAL_SECRETARY);
        token = "Bearer " + token;
        assertEquals("Medical_Secretary",Role.MEDICAL_SECRETARY.getDescription());
        assertEquals("test",jwtService.getUserName(token));
        assertEquals("test",jwtService.getEmail(token));
        assertEquals("test",jwtService.getId(token));
        assertEquals("test",jwtService.getName(token));
    }

}

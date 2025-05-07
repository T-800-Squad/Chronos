package moduloGestionUsuarios.UserManagement.controller;

import moduloGestionUsuarios.UserManagement.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteStudentControllerSuccess() {
        String idStudent = "123";

        // Simulamos que el estudiante existe y se elimina correctamente
        doNothing().when(userService).deleteStudent(idStudent);

        // Llamamos al método del controlador
        ResponseEntity<String> response = userController.deleteStudent(idStudent);

        // Verificamos que el método del servicio fue llamado
        verify(userService).deleteStudent(idStudent);

        // Verificamos la respuesta
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Estudiante eliminado", response.getBody());
    }

}

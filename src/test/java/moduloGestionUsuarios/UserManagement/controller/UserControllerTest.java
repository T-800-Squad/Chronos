package moduloGestionUsuarios.UserManagement.controller;

import moduloGestionUsuarios.UserManagement.DTO.AdminUpdateDTO;
import moduloGestionUsuarios.UserManagement.exception.UserManagementException;
import moduloGestionUsuarios.UserManagement.response.ApiResponse;
import moduloGestionUsuarios.UserManagement.service.UserServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(UserControllerTest.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserServiceInterface userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateAdminSuccessfully() throws Exception {
        AdminUpdateDTO dto = new AdminUpdateDTO();
        dto.setIdAdmin("admin123");
        doNothing().when(userService).addScheduleForAdmin(dto);

        ResponseEntity<ApiResponse<String>> response = userController.updateAdmin(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("Administrador Actualizado", response.getBody().getMessage());
        assertEquals("idadmin123", response.getBody().getData());


        verify(userService, times(1)).addScheduleForAdmin(dto);
    }

    @Test
    void testUpdateAdminThrowsException() throws Exception {
        AdminUpdateDTO dto = new AdminUpdateDTO();
        dto.setIdAdmin("admin123");

        doThrow(new UserManagementException("Error de prueba")).when(userService).addScheduleForAdmin(dto);

        UserManagementException exception = assertThrows(UserManagementException.class, () -> {
            userController.updateAdmin(dto);
        });

        assertEquals("Error de prueba", exception.getMessage());
    }
}

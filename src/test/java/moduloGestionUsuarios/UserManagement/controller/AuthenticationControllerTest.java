package moduloGestionUsuarios.UserManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import moduloGestionUsuarios.UserManagement.DTO.AdminRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.StudentRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.UserLogDTO;
import moduloGestionUsuarios.UserManagement.exception.UserManagementException;
import moduloGestionUsuarios.UserManagement.model.Administrator;
import moduloGestionUsuarios.UserManagement.service.AuthenticationServiceInterface;
import moduloGestionUsuarios.UserManagement.service.UserServiceInterface;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private AuthenticationServiceInterface authenticationService;
    @MockitoBean
    private UserServiceInterface userService;
    @Test
    public void testLogin() {
        try {
            String token = "123";
            when(authenticationService.authenticate(any(UserLogDTO.class))).thenReturn(token);
            String jsonBody = """
                {
                    "username": "admin",
                    "password": "admin"
                }
            """;
            mockMvc.perform(post("/authentication/login").content(jsonBody)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("200"))
                    .andExpect(jsonPath("$.message").value("Token de autenticacion"))
                    .andExpect(jsonPath("$.data").value("Bearer "+token));
        } catch (UserManagementException e) {
            fail();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testStudentRegister() throws Exception {
        StudentRegisterDTO dto = new StudentRegisterDTO();
        dto.setIdStudent("12345");
        dto.setCodeStudent("2020555444");
        dto.setStudentPassword("password");
        dto.setFullNameStudent("Juan Pérez");
        dto.setEmailAddress("juan@correo.com");
        dto.setAcademicProgram("Ingeniería");
        dto.setAddress("Calle 123");
        dto.setContactNumber("3100000000");
        dto.setTypeIdStudent("TI");
        dto.setBirthDate(LocalDate.parse("2002-05-13"));
        dto.setIdContact("789");
        dto.setFullNameContact("Madre Juan");
        dto.setTypeIdContact("CC");
        dto.setPhoneNumber("3200000000");
        dto.setRelationship("Madre");

        doNothing().when(userService).addStudent(any(StudentRegisterDTO.class));

        mockMvc.perform(post("/authentication/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Estudiante registrado correctamente"))
                .andExpect(jsonPath("$.data").value("Id: " + dto.getIdStudent()));
    }

    @Test
    void testAdminRegister() throws Exception {
        AdminRegisterDTO dto = new AdminRegisterDTO();
        dto.setIdAdmin("admin123");
        dto.setFullName("Admin Uno");
        dto.setAdminPassword("adminpass");
        dto.setEmailAddress("admin@correo.com");

        Administrator savedAdmin = new Administrator();
        savedAdmin.setIdAdmin("admin123");

        when(userService.addAdministrator(any(AdminRegisterDTO.class))).thenReturn(savedAdmin);

        mockMvc.perform(post("/authentication/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Administrador registrado correctamente"))
                .andExpect(jsonPath("$.data").value("Id: " + dto.getIdAdmin()));
    }

}

package moduloGestionUsuarios.UserManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import moduloGestionUsuarios.UserManagement.DTO.AdminRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.StudentRegisterDTO;
import moduloGestionUsuarios.UserManagement.service.UserServiceInterface;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private UserServiceInterface userService;

    @Autowired
    private ObjectMapper objectMapper;

    /*@Test
    public void testStudentRegister() throws Exception {
        StudentRegisterDTO dto = new StudentRegisterDTO();
        dto.setIdStudent("1234");
        dto.setCodeStudent("S001");
        dto.setStudentPassword("password");
        dto.setAddress("Calle Falsa 123");
        dto.setBirthDate(null);
        dto.setAcademicProgram("Ingeniería");
        dto.setContactNumber("3001112233");
        dto.setEmailAddress("student@test.com");
        dto.setTypeIdStudent("CC");
        dto.setFullNameStudent("Juan Pérez");

        dto.setIdContact("C123");
        dto.setFullNameContact("María Pérez");
        dto.setTypeIdContact("CC");
        dto.setPhoneNumber("3109999999");
        dto.setRelationship("Madre");

        mockMvc.perform(post("/user/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(userService, times(1)).addStudent(dto);
    }

    @Test
    public void testAdminRegister() throws Exception {
        AdminRegisterDTO dto = new AdminRegisterDTO();
        dto.setIdAdmin("A001");
        dto.setFullName("Carlos López");
        dto.setEmailAddress("admin@test.com");
        dto.setContactNumber("3118888877");
        dto.setTypeId("CC");
        dto.setRole("Administrador");
        dto.setSpecialty("Sistemas");
        dto.setAdminPassword("adminPass");

        mockMvc.perform(post("/user/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(userService, times(1)).addAdministrator(dto);
    }*/
}

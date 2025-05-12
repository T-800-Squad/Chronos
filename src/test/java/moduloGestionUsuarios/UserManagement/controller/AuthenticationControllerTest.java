package moduloGestionUsuarios.UserManagement.controller;

import moduloGestionUsuarios.UserManagement.DTO.UserLogDTO;
import moduloGestionUsuarios.UserManagement.exception.UserManagementException;
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


import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AuthenticationServiceInterface authenticationService;
    @MockitoBean
    private UserServiceInterface userService;
    @Test
    public void testLogin() {
        try {
            String token = "123";
            Mockito.when(authenticationService.authenticate(Mockito.any(UserLogDTO.class))).thenReturn(token);
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
}

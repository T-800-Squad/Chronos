package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.DTO.UserLogDTO;
import moduloGestionUsuarios.UserManagement.exception.UserManagementException;
import moduloGestionUsuarios.UserManagement.model.Administrator;
import moduloGestionUsuarios.UserManagement.model.Role;
import moduloGestionUsuarios.UserManagement.model.Student;
import moduloGestionUsuarios.UserManagement.repository.AdministratorRepositoryJPA;
import moduloGestionUsuarios.UserManagement.repository.StudentRepositoryJPA;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class AuthenticationServiceTest {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PasswordEncoder encoder;

    @MockitoBean
    private StudentRepositoryJPA studentRepository;

    @MockitoBean
    private AdministratorRepositoryJPA administratorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testAuthenticateIncorrectUserName(){
        Mockito.when(studentRepository.findByEmailAddress("email@mail.escuelaing.edu.co")).thenReturn(Optional.empty());
        Mockito.when(administratorRepository.findByEmailAddress("email@mail.escuelaing.edu.co")).thenReturn(Optional.empty());
        UserLogDTO userLogDTO = new UserLogDTO("email",encoder.encode("1234"));
        try{
            authenticationService.authenticate(userLogDTO);
        }catch (UserManagementException e){
            assertEquals("El nombre de usuario no fue encontrado",e.getMessage());
        }
    }
    @Test
    public void testAuthenticateIncorrectPasswordOfStudent(){
        Student student = new Student();
        student.setEmailAddress("email@mail.escuelaing.edu.co");
        student.setStudentPassword(passwordEncoder.encode("1234"));
        Mockito.when(studentRepository.findByEmailAddress("email@mail.escuelaing.edu.co")).thenReturn(Optional.of(student));
        Mockito.when(administratorRepository.findByEmailAddress("email@escuelaing.edu.co")).thenReturn(Optional.empty());
        UserLogDTO userLogDTO = new UserLogDTO("email","123");
        try{
            authenticationService.authenticate(userLogDTO);
        }catch (UserManagementException e){
            assertEquals("La contraseña es incorrecta",e.getMessage());
        }
    }

    @Test
    public void testAuthenticateIncorrectPasswordOfAdministrator(){
        Administrator administrator = new Administrator();
        administrator.setEmailAddress("email@escuelaing.edu.co");
        administrator.setAdminPassword(encoder.encode("1234"));
        Mockito.when(studentRepository.findByEmailAddress("email@mail.escuelaing.edu.co")).thenReturn(Optional.empty());
        Mockito.when(administratorRepository.findByEmailAddress("email@escuelaing.edu.co")).thenReturn(Optional.of(administrator));
        UserLogDTO userLogDTO = new UserLogDTO("email","123");
        try{
            authenticationService.authenticate(userLogDTO);
        }catch (UserManagementException e){
            assertEquals("La contraseña es incorrecta",e.getMessage());
        }
    }

    @Test
    public void testAuthenticateCorrectPasswordOfStudent(){
        Student student = new Student();
        student.setEmailAddress("email@mail.escuelaing.edu.co");
        student.setStudentPassword(encoder.encode("1234"));
        Mockito.when(studentRepository.findByEmailAddress("email@mail.escuelaing.edu.co")).thenReturn(Optional.of(student));
        Mockito.when(administratorRepository.findByEmailAddress("email@escuelaing.edu.co")).thenReturn(Optional.empty());
        UserLogDTO userLogDTO = new UserLogDTO("email","1234");
        try{
            authenticationService.authenticate(userLogDTO);
        }catch (UserManagementException e){
            fail();
        }
    }

    @Test
    public void testAuthenticateCorrectPasswordOfAdministrator(){
        Administrator administrator = new Administrator();
        administrator.setEmailAddress("email@escuelaing.edu.co");
        administrator.setAdminPassword(encoder.encode("1234"));
        administrator.setRole(Role.ADMIN);
        Mockito.when(studentRepository.findByEmailAddress("email@mail.escuelaing.edu.co")).thenReturn(Optional.empty());
        Mockito.when(administratorRepository.findByEmailAddress("email@escuelaing.edu.co")).thenReturn(Optional.of(administrator));
        UserLogDTO userLogDTO = new UserLogDTO("email","1234");
        try{
            authenticationService.authenticate(userLogDTO);
        }catch (UserManagementException e){
            fail();
        }
    }

}

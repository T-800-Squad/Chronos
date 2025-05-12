package moduloGestionUsuarios.UserManagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import moduloGestionUsuarios.UserManagement.DTO.UserDTO;
import moduloGestionUsuarios.UserManagement.exception.UserManagementException;
import moduloGestionUsuarios.UserManagement.model.Administrator;
import moduloGestionUsuarios.UserManagement.model.Student;
import moduloGestionUsuarios.UserManagement.model.Role;
import moduloGestionUsuarios.UserManagement.repository.AdministratorRepositoryJPA;
import moduloGestionUsuarios.UserManagement.repository.StudentRepositoryJPA;

import java.util.Collections;
import java.util.List;

public class QueryServiceTest {
    
    @InjectMocks
    private QueryService queryService;

    @Mock
    private StudentRepositoryJPA studentRepository;

    @Mock
    private AdministratorRepositoryJPA administratorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testQueryByFullName_Found() throws UserManagementException {
        UserDTO userDTO = new UserDTO("Rosa Martinez", null, null, null, null);
        Student student = new Student();
        student.setFullName("Rosa Martinez");
        student.setAcademicProgram("Ingenieria de sistemas");
        student.setCodeStudent("12345");
        student.setIdStudent("54321");
        Administrator admin = new Administrator();
        admin.setFullName("Rosa Martinez");
        admin.setIdAdmin("98765");
        admin.setRole(Role.ADMIN);

        when(studentRepository.findByFullName("Rosa Martinez")).thenReturn(List.of(student));
        when(administratorRepository.findByFullName("Rosa Martinez")).thenReturn(List.of(admin));

        List<UserDTO> result = queryService.query(userDTO);

        assertEquals(2, result.size());
    }

    @Test
    void testQueryByFullName_NotFound() {
        UserDTO userDTO = new UserDTO("Noexiste Nombre", null, null, null, null);
        when(studentRepository.findByFullName("Noexiste Nombre")).thenReturn(Collections.emptyList());
        when(administratorRepository.findByFullName("Noexiste Nombre")).thenReturn(Collections.emptyList());

        assertThrows(UserManagementException.class, () -> queryService.query(userDTO));
    }

    @Test
    void testQueryByAcademicProgram_Found() throws UserManagementException {
        UserDTO userDTO = new UserDTO(null, "Ingenieria ambiental", null, null, null);
        Student student = new Student();
        student.setFullName("Alice Rojas");
        student.setAcademicProgram("Ingenieria ambiental");
        student.setCodeStudent("67890");
        student.setIdStudent("09876");

        when(studentRepository.findByAcademicProgram("Ingenieria ambiental")).thenReturn(List.of(student));

        List<UserDTO> result = queryService.query(userDTO);

        assertEquals(1, result.size());
    }

    @Test
    void testQueryByRole_NotFound() {
        UserDTO userDTO = new UserDTO(null, null, null, "Noexiste Role", null);
        when(administratorRepository.findByRole(any())).thenReturn(Collections.emptyList());

        assertThrows(UserManagementException.class, () -> queryService.query(userDTO));
    }
}

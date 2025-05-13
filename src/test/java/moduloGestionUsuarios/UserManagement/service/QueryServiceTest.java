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
        UserDTO userDTO = new UserDTO(null, "Ingenieria Ambiental", null, null, null);
        Student student = new Student();
        student.setFullName("Alice Rojas");
        student.setAcademicProgram("Ingenieria Ambiental");
        student.setCodeStudent("67890");
        student.setIdStudent("09876");

        when(studentRepository.findByAcademicProgram("Ingenieria Ambiental")).thenReturn(List.of(student));

        List<UserDTO> result = queryService.query(userDTO);

        assertEquals(1, result.size());
    }

    @Test
    void testQueryByAcademicProgram_NotFound() {
        UserDTO userDTO = new UserDTO(null, "Noexiste Programa", null, null, null);
        when(studentRepository.findByAcademicProgram("Noexiste Programa")).thenReturn(Collections.emptyList());

        assertThrows(UserManagementException.class, () -> queryService.query(userDTO));
    }

    @Test
    void testQueryByCodeStudent_Found() throws UserManagementException {
        UserDTO userDTO = new UserDTO(null, null, "12345", null, null);
        Student student = new Student();
        student.setFullName("Pedro López");
        student.setAcademicProgram("Ingenieria Mecanica");
        student.setCodeStudent("89999");
        student.setIdStudent("99998");

        when(studentRepository.findByCodeStudent("89999")).thenReturn(List.of(student));

        List<UserDTO> result = queryService.query(userDTO);

        assertEquals(1, result.size());
    }

    @Test
    void testQueryByCodeStudent_NotFound() {
        UserDTO userDTO = new UserDTO(null, null, "52345", null, null);
        when(studentRepository.findByCodeStudent("52345")).thenReturn(Collections.emptyList());

        assertThrows(UserManagementException.class, () -> queryService.query(userDTO));
    }

    @Test
    void testQueryById_Found() throws UserManagementException {
        UserDTO userDTO = new UserDTO(null, null, null, null, "54321");
        Student student = new Student();
        student.setFullName("Ana María");
        student.setAcademicProgram("Matematicas");
        student.setCodeStudent("11223");
        student.setIdStudent("54311");

        when(studentRepository.findByIdStudent("54321")).thenReturn(List.of(student));
        when(administratorRepository.findByIdAdmin("54311")).thenReturn(Collections.emptyList());

        List<UserDTO> result = queryService.query(userDTO);

        assertEquals(1, result.size());
    }

    @Test
    void testQueryById_NotFound() {
        UserDTO userDTO = new UserDTO(null, null, null, null, "noexiste");
        when(studentRepository.findByIdStudent("noexiste")).thenReturn(Collections.emptyList());
        when(administratorRepository.findByIdAdmin("noexiste")).thenReturn(Collections.emptyList());

        assertThrows(UserManagementException.class, () -> queryService.query(userDTO));
    }

    @Test
    void testQueryByRole_NotFound() {
        UserDTO userDTO = new UserDTO(null, null, null, "Noexiste Role", null);
        when(administratorRepository.findByRole(any())).thenReturn(Collections.emptyList());

        assertThrows(UserManagementException.class, () -> queryService.query(userDTO));
    }

    @Test
    void testQueryByFullNameAndProgram_Found() throws UserManagementException {
        UserDTO userDTO = new UserDTO("Luis Gomez", "Ingenieria de sistemas", null, null, null);

        Student student = new Student();
        student.setFullName("Luis Gomez");
        student.setAcademicProgram("Ingenieria de sistemas");
        student.setCodeStudent("11111");
        student.setIdStudent("22222");

        when(studentRepository.findByFullName("Luis Gomez")).thenReturn(List.of(student));
        when(administratorRepository.findByFullName("Luis Gomez")).thenReturn(Collections.emptyList());
        when(studentRepository.findByAcademicProgram("Ingenieria de sistemas")).thenReturn(List.of(student));

        List<UserDTO> result = queryService.query(userDTO);

        assertEquals(1, result.size());
    }

    @Test
    void testQueryByFullNameAndCodeStudent_Found() throws UserManagementException {
        UserDTO userDTO = new UserDTO("Carlos Peña", null, "10001", null, null);

        Student student = new Student();
        student.setFullName("Carlos Peña");
        student.setAcademicProgram("Ingenieria Civil");
        student.setCodeStudent("10001");
        student.setIdStudent("50001");

        when(studentRepository.findByFullName("Carlos Peña")).thenReturn(List.of(student));
        when(administratorRepository.findByFullName("Carlos Peña")).thenReturn(Collections.emptyList());
        when(studentRepository.findByCodeStudent("10001")).thenReturn(List.of(student));

        List<UserDTO> result = queryService.query(userDTO);

        assertEquals(1, result.size());
    }

    @Test
    void testQueryByFullNameAndProgram_NoIntersection() {
        UserDTO userDTO = new UserDTO("Maria Ruiz", "Ingenieria Electronica", null, null, null);

        Student studentByName = new Student();
        studentByName.setFullName("Maria Ruiz");
        studentByName.setAcademicProgram("Ingenieria de Sistemas");
        studentByName.setCodeStudent("20001");
        studentByName.setIdStudent("60001");

        Student studentByProgram = new Student();
        studentByProgram.setFullName("Otra Persona");
        studentByProgram.setAcademicProgram("Ingenieria Electronica");
        studentByProgram.setCodeStudent("99899");
        studentByProgram.setIdStudent("98998");

        when(studentRepository.findByFullName("Maria Ruiz")).thenReturn(List.of(studentByName));
        when(administratorRepository.findByFullName("Maria Ruiz")).thenReturn(Collections.emptyList());
        when(studentRepository.findByAcademicProgram("Ingenieria Electronica")).thenReturn(List.of(studentByProgram));

        assertThrows(UserManagementException.class, () -> queryService.query(userDTO));
    }

}

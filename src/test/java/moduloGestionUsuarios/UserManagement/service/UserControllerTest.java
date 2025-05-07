package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.DTO.UserUpdateDTO;
import moduloGestionUsuarios.UserManagement.controller.UserController;
import moduloGestionUsuarios.UserManagement.model.EmergencyContact;
import moduloGestionUsuarios.UserManagement.model.Student;
import moduloGestionUsuarios.UserManagement.repository.EmergencyContactRepositoryJPA;
import moduloGestionUsuarios.UserManagement.repository.StudentRepositoryJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest  {

    @InjectMocks
    private UserService userService;

    @Mock
    private StudentRepositoryJPA studentRepository;

    @InjectMocks
    private UserController userController;

    @Mock
    private EmergencyContactRepositoryJPA emergencyContactRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateStudentSuccess() {
        // Crear DTO
        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setIdStudent("123");
        dto.setAddress("Nueva dirección");
        dto.setAcademicProgram("Nuevo programa");
        dto.setContactNumber("1111111");
        dto.setTypeIdStudent("TI");

        dto.setIdContact("999");
        dto.setFullNameContact("Nuevo Nombre");
        dto.setTypeIdContact("CC");
        dto.setPhoneNumber("3003003000");
        dto.setRelationship("Hermano");

        // Objetos actuales en la base de datos simulada
        Student student = new Student();
        student.setId("123");

        EmergencyContact contact = new EmergencyContact();
        contact.setId("999");

        // Mock de los repositorios
        when(studentRepository.findById("123")).thenReturn(Optional.of(student));
        when(emergencyContactRepository.findById("999")).thenReturn(Optional.of(contact));

        // Ejecutar el método
        userService.updateStudent(dto);

        // Verificar cambios en Student
        assertEquals("Nueva dirección", student.getAddress());
        assertEquals("Nuevo programa", student.getAcademicProgram());
        assertEquals("1111111", student.getContactNumber());
        assertEquals("TI", student.getTypeId());

        // Verificar cambios en EmergencyContact
        assertEquals("Nuevo Nombre", contact.getFullName());
        assertEquals("CC", contact.getTypeId());
        assertEquals("3003003000", contact.getPhoneNumber());
        assertEquals("Hermano", contact.getRelationship());

        verify(studentRepository).save(student);
        verify(emergencyContactRepository).save(contact);
    }

    @Test
    void testUpdateStudentThrowsWhenStudentNotFound() {
        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setIdStudent("noExiste");
        dto.setIdContact("999");

        when(studentRepository.findById("noExiste")).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.updateStudent(dto);
        });

        assertEquals("Estudiante no encontrado", thrown.getMessage());
    }

    @Test
    void testUpdateStudentThrowsWhenContactNotFound() {
        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setIdStudent("123");
        dto.setIdContact("noExiste");

        Student student = new Student();
        student.setId("123");

        when(studentRepository.findById("123")).thenReturn(Optional.of(student));
        when(emergencyContactRepository.findById("noExiste")).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.updateStudent(dto);
        });

        assertEquals("Contacto de emergencia no encontrado", thrown.getMessage());
    }

}

package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.DTO.StudentRegisterDTO;
import moduloGestionUsuarios.UserManagement.model.Administrator;
import moduloGestionUsuarios.UserManagement.model.EmergencyContact;
import moduloGestionUsuarios.UserManagement.model.Student;
import moduloGestionUsuarios.UserManagement.repository.AdministratorRepositoryJPA;
import moduloGestionUsuarios.UserManagement.repository.EmergencyContactRepositoryJPA;
import moduloGestionUsuarios.UserManagement.repository.StudentRepositoryJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private StudentRepositoryJPA studentRepository;

    @Mock
    private AdministratorRepositoryJPA administratorRepository;

    @Mock
    private EmergencyContactRepositoryJPA emergencyContactRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Captor
    private ArgumentCaptor<Student> studentCaptor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddStudent() {

        StudentRegisterDTO dto = new StudentRegisterDTO();
        dto.setIdStudent("123");
        dto.setCodeStudent("STU001");
        dto.setStudentPassword("pass123");
        dto.setAddress("Calle Falsa 123");
        dto.setBirthDate(java.time.LocalDate.of(2000, 1, 1));
        dto.setAcademicProgram("Ingeniería");
        dto.setContactNumber("3210000000");
        dto.setEmailAddress("student@example.com");
        dto.setTypeIdStudent("CC");
        dto.setFullNameStudent("Juan Pérez");

        dto.setIdContact("456");
        dto.setFullNameContact("Carlos Pérez");
        dto.setTypeIdContact("CC");
        dto.setPhoneNumber("3101234567");
        dto.setRelationship("Padre");

        when(passwordEncoder.encode("pass123")).thenReturn("encodedPass");

        userService.addStudent(dto);

        verify(studentRepository, times(1)).save(studentCaptor.capture());

        Student saved = studentCaptor.getValue();

        assert saved.getIdStudent().equals("123");
        assert saved.getStudentPassword().equals("encodedPass");
        assert saved.getEmergencyContacts().size() == 1;

        EmergencyContact contact = saved.getEmergencyContacts().get(0);
        assert contact.getFullName().equals("Carlos Pérez");
    }

    /*@Test
    public void testAddAdministrator() {
        Administrator admin = new Administrator();
        admin.setIdAdmin("admin001");
        admin.setFullName("Ana Gómez");
        admin.setEmailAddress("ana@example.com");
        admin.setContactNumber("3001234567");
        admin.setTypeId("CC");
        admin.setRole("Coordinadora");
        admin.setSpecialty("Ingeniería de Sistemas");
        admin.setAdminPassword("adminPass");

        when(passwordEncoder.encode("adminPass")).thenReturn("encodedAdminPass");
        when(administratorRepository.save(any(Administrator.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Administrator savedAdmin = userService.addAdministrator(admin);

        assert savedAdmin.getFullName().equals("Ana Gómez");
        assert savedAdmin.getAdminPassword().equals("encodedAdminPass");

        ArgumentCaptor<Administrator> adminCaptor = ArgumentCaptor.forClass(Administrator.class);
        verify(administratorRepository, times(1)).save(adminCaptor.capture());

        Administrator captured = adminCaptor.getValue();
        assert captured.getIdAdmin().equals("admin001");
        assert captured.getEmailAddress().equals("ana@example.com");
    }*/

}

package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.DTO.AdminRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.StudentRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.UserUpdateDTO;
import moduloGestionUsuarios.UserManagement.controller.UserController;
import moduloGestionUsuarios.UserManagement.model.Administrator;
import moduloGestionUsuarios.UserManagement.model.EmergencyContact;
import moduloGestionUsuarios.UserManagement.model.Schedule;
import moduloGestionUsuarios.UserManagement.model.Student;
import moduloGestionUsuarios.UserManagement.repository.AdministratorRepositoryJPA;
import moduloGestionUsuarios.UserManagement.repository.EmergencyContactRepositoryJPA;
import moduloGestionUsuarios.UserManagement.repository.ScheduleRepository;
import moduloGestionUsuarios.UserManagement.repository.StudentRepositoryJPA;
import moduloGestionUsuarios.UserManagement.exception.UserManagementException;
import moduloGestionUsuarios.UserManagement.model.Role;
import moduloGestionUsuarios.UserManagement.model.Specialty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest  {

    @InjectMocks
    private UserService userService;

    @Mock
    private StudentRepositoryJPA studentRepository;

    @InjectMocks
    private UserController userController;

    @Mock
    private EmergencyContactRepositoryJPA emergencyContactRepository;

    @Mock
    private AdministratorRepositoryJPA administratorRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

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

    //
    @Test
    void addAdministrator_shouldSaveAdministratorWithSchedules() {
        AdminRegisterDTO dto = new AdminRegisterDTO();
        dto.setIdAdmin("123");
        dto.setFullName("Admin Uno");
        dto.setAdminPassword("adminpass");
        dto.setEmailAddress("admin@correo.com");
        dto.setContactNumber("3000000000");
        dto.setRole("ADMIN");
        dto.setSpecialty("TI");
        dto.setTypeId("CC");
        dto.setSchedule(Arrays.asList(1)); // ID de prueba

        Schedule schedule = new Schedule(); // objeto ficticio
        when(scheduleRepository.findById(Integer.valueOf("1"))).thenReturn(Optional.of(schedule));
        when(passwordEncoder.encode("adminpass")).thenReturn("hashedpass");
        when(administratorRepository.save(any(Administrator.class))).thenAnswer(i -> i.getArgument(0));

        try {
            Administrator saved = userService.addAdministrator(dto);
            assertEquals("hashedpass", saved.getAdminPassword());
            assertEquals(1, saved.getSchedules().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void addStudent_shouldSaveStudentWithEmergencyContact() {
        StudentRegisterDTO dto = new StudentRegisterDTO();
        dto.setIdStudent("456");
        dto.setCodeStudent("2020112233");
        dto.setStudentPassword("password");
        dto.setFullNameStudent("Estudiante Uno");
        dto.setEmailAddress("est@correo.com");
        dto.setAcademicProgram("Ingeniería");
        dto.setAddress("Cra 1 # 2-3");
        dto.setContactNumber("3100000000");
        dto.setTypeIdStudent("TI");
        dto.setBirthDate(LocalDate.parse("2000-01-01"));
        dto.setIdContact("789");
        dto.setFullNameContact("Contacto Uno");
        dto.setTypeIdContact("CC");
        dto.setPhoneNumber("3200000000");
        dto.setRelationship("Madre");

        when(passwordEncoder.encode("password")).thenReturn("hashedStudentPassword");

        Student savedStudent = new Student();
        when(studentRepository.saveAndFlush(any(Student.class))).thenReturn(savedStudent);
        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        try{userService.addStudent(dto);} catch (Exception e) {e.printStackTrace();}

        verify(emergencyContactRepository).save(any(EmergencyContact.class));
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    //
    @Test
    public void shouldAddAdministratorSuccessfully() throws UserManagementException {
        AdminRegisterDTO dto = new AdminRegisterDTO();
        dto.setIdAdmin("A123");
        dto.setTypeId("CC");
        dto.setFullName("Carlos Torres");
        dto.setContactNumber("1234567890");
        dto.setEmailAddress("carlos@mail.escuelaing.edu.co");
        dto.setAdminPassword("securepass");
        dto.setRole("ADMIN");

        when(passwordEncoder.encode("securepass")).thenReturn("hashedPass");
        when(administratorRepository.save(any(Administrator.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Administrator savedAdmin = userService.addAdministrator(dto);

        assertEquals("Carlos Torres", savedAdmin.getFullName());
        assertEquals(Role.ADMIN, savedAdmin.getRole());
        assertEquals("hashedPass", savedAdmin.getAdminPassword());
    }

    @Test
    public void shouldThrowExceptionWhenDoctorWithoutSpecialty() {
        AdminRegisterDTO dto = new AdminRegisterDTO();
        dto.setIdAdmin("1014567891");
        dto.setTypeId("CC");
        dto.setFullName("Dra. Ana Ruiz");
        dto.setContactNumber("987654321");
        dto.setEmailAddress("ana@mail.escuelaing.edu.co");
        dto.setAdminPassword("medicpass");
        dto.setRole("DOCTOR");
        dto.setSpecialty(null);

        UserManagementException exception = assertThrows(UserManagementException.class,
                () -> userService.addAdministrator(dto));
        assertEquals("Error inesperado: La especialidad es obligatoria para el rol DOCTOR.", exception.getMessage());
    }

    @Test
    public void shouldSetSpecialtyWhenRoleIsDoctor() throws UserManagementException {
        AdminRegisterDTO dto = new AdminRegisterDTO();
        dto.setIdAdmin("D002");
        dto.setTypeId("TI");
        dto.setFullName("Dra. Laura García");
        dto.setContactNumber("321654987");
        dto.setEmailAddress("laura@mail.escuelaing.edu.co");
        dto.setAdminPassword("docpass");
        dto.setRole("DOCTOR");
        dto.setSpecialty("PSICOLOGY");

        when(passwordEncoder.encode("docpass")).thenReturn("encodedDocPass");
        when(administratorRepository.save(any(Administrator.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Administrator admin = userService.addAdministrator(dto);

        assertEquals(Role.DOCTOR, admin.getRole());
        assertEquals(Specialty.PSICOLOGY, admin.getSpecialty());
    }
}

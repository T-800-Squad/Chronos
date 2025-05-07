package moduloGestionUsuarios.UserManagement.controller;

import moduloGestionUsuarios.UserManagement.DTO.AdminRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.StudentRegisterDTO;
import moduloGestionUsuarios.UserManagement.model.Administrator;
import moduloGestionUsuarios.UserManagement.model.EmergencyContact;
import moduloGestionUsuarios.UserManagement.model.Schedule;
import moduloGestionUsuarios.UserManagement.model.Student;
import moduloGestionUsuarios.UserManagement.repository.AdministratorRepositoryJPA;
import moduloGestionUsuarios.UserManagement.repository.EmergencyContactRepositoryJPA;
import moduloGestionUsuarios.UserManagement.repository.ScheduleRepository;
import moduloGestionUsuarios.UserManagement.repository.StudentRepositoryJPA;
import moduloGestionUsuarios.UserManagement.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private StudentRepositoryJPA studentRepository;

    @Mock
    private AdministratorRepositoryJPA administratorRepository;

    @Mock
    private EmergencyContactRepositoryJPA emergencyContactRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

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
        when(scheduleRepository.findById("1")).thenReturn(Optional.of(schedule));
        when(passwordEncoder.encode("adminpass")).thenReturn("hashedpass");
        when(administratorRepository.save(any(Administrator.class))).thenAnswer(i -> i.getArgument(0));

        Administrator saved = userService.addAdministrator(dto);

        assertEquals("hashedpass", saved.getAdminPassword());
        assertEquals(1, saved.getSchedules().size());
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

        userService.addStudent(dto);

        verify(emergencyContactRepository).save(any(EmergencyContact.class));
        verify(studentRepository, times(1)).save(any(Student.class));
    }
}

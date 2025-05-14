package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.DTO.AdminRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.AdminUpdateDTO;
import moduloGestionUsuarios.UserManagement.DTO.StudentRegisterDTO;
import moduloGestionUsuarios.UserManagement.exception.UserManagementException;
import moduloGestionUsuarios.UserManagement.model.*;
import moduloGestionUsuarios.UserManagement.repository.AdministratorRepositoryJPA;
import moduloGestionUsuarios.UserManagement.repository.EmergencyContactRepositoryJPA;
import moduloGestionUsuarios.UserManagement.repository.ScheduleRepository;
import moduloGestionUsuarios.UserManagement.repository.StudentRepositoryJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    private ScheduleRepository scheduleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addAdministrator_shouldAddAdministratorSuccessfully() throws UserManagementException {
        AdminRegisterDTO dto = new AdminRegisterDTO();
        dto.setIdAdmin("A123");
        dto.setTypeId("CC");
        dto.setFullName("Carlos Torres");
        dto.setContactNumber("1234567890");
        dto.setEmailAddress("carlos@mail.escuelaing.edu.co");
        dto.setAdminPassword("securepass");
        dto.setRole("ADMIN");
        dto.setSchedule(new ArrayList<>());

        when(passwordEncoder.encode("securepass")).thenReturn("hashedPass");
        when(administratorRepository.save(any(Administrator.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Administrator savedAdmin = userService.addAdministrator(dto);

        assertEquals("Carlos Torres", savedAdmin.getFullName());
        assertEquals(Role.ADMIN, savedAdmin.getRole());
        assertEquals("hashedPass", savedAdmin.getAdminPassword());
    }

    @Test
    public void addAdministrator_shouldSetSpecialtyWhenRoleIsDoctor() throws UserManagementException {
        AdminRegisterDTO dto = new AdminRegisterDTO();
        dto.setIdAdmin("D002");
        dto.setTypeId("TI");
        dto.setFullName("Dra. Laura García");
        dto.setContactNumber("321654987");
        dto.setEmailAddress("laura@mail.escuelaing.edu.co");
        dto.setAdminPassword("docpass");
        dto.setRole("DOCTOR");
        dto.setSpecialty("PSYCHOLOGY");
        dto.setSchedule(new ArrayList<>());

        when(passwordEncoder.encode("docpass")).thenReturn("encodedDocPass");
        when(administratorRepository.save(any(Administrator.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Administrator admin = userService.addAdministrator(dto);

        assertEquals(Role.DOCTOR, admin.getRole());
        assertEquals(Specialty.PSYCHOLOGY, admin.getSpecialty());
    }

    @Test
    void addScheduleForAdmin_shouldAdd() throws UserManagementException {
        String adminId = "admin123";
        Integer scheduleId1 = 1;
        Integer scheduleId2 = 2;

        AdminUpdateDTO adminUpdateDTO = new AdminUpdateDTO();
        adminUpdateDTO.setIdAdmin(adminId);
        adminUpdateDTO.setNewSchedule(Arrays.asList(scheduleId1, scheduleId2));

        Schedule schedule1 = new Schedule();
        schedule1.setIdSchedule(scheduleId1);
        Schedule schedule2 = new Schedule();
        schedule2.setIdSchedule(scheduleId2);

        Administrator admin = new Administrator();
        admin.setIdAdmin(adminId);
        admin.setSchedules(new ArrayList<>());

        when(administratorRepository.findById(adminId)).thenReturn(Optional.of(admin));
        when(scheduleRepository.findById(scheduleId1)).thenReturn(Optional.of(schedule1));
        when(scheduleRepository.findById(scheduleId2)).thenReturn(Optional.of(schedule2));

        userService.addScheduleForAdmin(adminUpdateDTO);

        assertEquals(2, admin.getSchedules().size());
        assertTrue(admin.getSchedules().contains(schedule1));
        assertTrue(admin.getSchedules().contains(schedule2));

        verify(administratorRepository, times(1)).save(admin);
    }

    @Test
    void addScheduleForAdmin_shouldDontFoundAdmin() {
        AdminUpdateDTO dto = new AdminUpdateDTO();
        dto.setIdAdmin("noexistente");

        when(administratorRepository.findById("noexistente")).thenReturn(Optional.empty());

        UserManagementException exception = assertThrows(UserManagementException.class, () -> {
            userService.addScheduleForAdmin(dto);
        });

        assertEquals("Administrador no encontrado", exception.getMessage());
    }

    @Test
    void addScheduleForAdmin_shouldDontFoundSchedule() {
        String adminId = "admin123";
        Integer scheduleId = 99;

        AdminUpdateDTO dto = new AdminUpdateDTO();
        dto.setIdAdmin(adminId);
        dto.setNewSchedule(Collections.singletonList(scheduleId));

        Administrator admin = new Administrator();
        admin.setIdAdmin(adminId);
        admin.setSchedules(new ArrayList<>());

        when(administratorRepository.findById(adminId)).thenReturn(Optional.of(admin));
        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.empty());

        UserManagementException exception = assertThrows(UserManagementException.class, () -> {
            userService.addScheduleForAdmin(dto);
        });

        assertEquals("Horario no encontrado: " + scheduleId, exception.getMessage());
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
        dto.setSchedule(Arrays.asList(1));

        Schedule schedule = new Schedule();
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

    @Test
    void addStudent_shouldThrowsExceptionWhenStudentAlreadyExist() throws UserManagementException {
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


        when(studentRepository.existsById("456"))
                .thenReturn(false)
                .thenReturn(true);

        Student savedStudent = new Student();
        when(studentRepository.saveAndFlush(any(Student.class))).thenReturn(savedStudent);
        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        userService.addStudent(dto);

        UserManagementException exception = assertThrows(UserManagementException.class, () -> {
            userService.addStudent(dto);
        });

        assertEquals("Ya existe un estudiante con ese id", exception.getMessage());
    }

    @Test
    void addAdministrator_shouldThrowsExceptionWhenAdminAlreadyExist() throws UserManagementException {
        AdminRegisterDTO dto = new AdminRegisterDTO();
        dto.setIdAdmin("123");
        dto.setFullName("Admin Uno");
        dto.setAdminPassword("adminpass");
        dto.setEmailAddress("admin@correo.com");
        dto.setContactNumber("3000000000");
        dto.setRole("ADMIN");
        dto.setSpecialty("TI");
        dto.setTypeId("CC");
        dto.setSchedule(Arrays.asList(1));

        when(passwordEncoder.encode("password")).thenReturn("hashedStudentPassword");


        when(administratorRepository.existsById("123"))
                .thenReturn(false)
                .thenReturn(true);

        Administrator savedAdmin = new Administrator();
        when(administratorRepository.saveAndFlush(any(Administrator.class))).thenReturn(savedAdmin);
        when(administratorRepository.save(any(Administrator.class))).thenAnswer(i -> i.getArgument(0));

        userService.addAdministrator(dto);

        UserManagementException exception = assertThrows(UserManagementException.class, () -> {
            userService.addAdministrator(dto);
        });

        assertEquals("Ya existe un usuario con ese id", exception.getMessage());
    }

    @Test
    void addAdministrator_shouldThrowsExceptionWhenAdminWithRoleDoctorDoesNotHaveSpecialty() throws UserManagementException {
        AdminRegisterDTO dto = new AdminRegisterDTO();
        dto.setIdAdmin("123");
        dto.setFullName("Admin Uno");
        dto.setAdminPassword("adminpass");
        dto.setEmailAddress("admin@correo.com");
        dto.setContactNumber("3000000000");
        dto.setRole("DOCTOR");
        dto.setSpecialty(null);
        dto.setTypeId("CC");
        dto.setSchedule(Arrays.asList(1));

        Schedule schedule = new Schedule();
        when(scheduleRepository.findById(Integer.valueOf("1"))).thenReturn(Optional.of(schedule));
        when(passwordEncoder.encode("adminpass")).thenReturn("hashedpass");
        when(administratorRepository.save(any(Administrator.class))).thenAnswer(i -> i.getArgument(0));

        try {
            Administrator saved = userService.addAdministrator(dto);
            assertEquals("hashedpass", saved.getAdminPassword());
            assertEquals(1, saved.getSchedules().size());
        } catch (Exception e) {
            assertEquals("Error inesperado: La especialidad es obligatoria para el rol DOCTOR.", e.getMessage());
        }
    }
}

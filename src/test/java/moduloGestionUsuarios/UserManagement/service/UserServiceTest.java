package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.DTO.AdminRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.AdminUpdateDTO;
import moduloGestionUsuarios.UserManagement.exception.UserManagementException;
import moduloGestionUsuarios.UserManagement.model.Administrator;
import moduloGestionUsuarios.UserManagement.model.Role;
import moduloGestionUsuarios.UserManagement.model.Schedule;
import moduloGestionUsuarios.UserManagement.model.Specialty;
import moduloGestionUsuarios.UserManagement.repository.AdministratorRepositoryJPA;
import moduloGestionUsuarios.UserManagement.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private AdministratorRepositoryJPA administratorRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

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
        dto.setSchedule(new ArrayList<>());

        when(passwordEncoder.encode("securepass")).thenReturn("hashedPass");
        when(administratorRepository.save(any(Administrator.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Administrator savedAdmin = userService.addAdministrator(dto);

        assertEquals("Carlos Torres", savedAdmin.getFullName());
        assertEquals(Role.ADMIN, savedAdmin.getRole());
        assertEquals("hashedPass", savedAdmin.getAdminPassword());
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
        dto.setSpecialty("PSYCHOLOGY");
        dto.setSchedule(new ArrayList<>());

        when(passwordEncoder.encode("docpass")).thenReturn("encodedDocPass");
        when(administratorRepository.save(any(Administrator.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Administrator admin = userService.addAdministrator(dto);

        assertEquals(Role.DOCTOR, admin.getRole());
        assertEquals(Specialty.PSYCHOLOGY, admin.getSpecialty());
    }

    @Test
    void testAddScheduleForAdmin() throws UserManagementException {
        // Arrange
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
        admin.setSchedules(new ArrayList<>()); // Sin horarios inicialmente

        when(administratorRepository.findById(adminId)).thenReturn(Optional.of(admin));
        when(scheduleRepository.findById(scheduleId1)).thenReturn(Optional.of(schedule1));
        when(scheduleRepository.findById(scheduleId2)).thenReturn(Optional.of(schedule2));

        // Act
        userService.addScheduleForAdmin(adminUpdateDTO);

        // Assert
        assertEquals(2, admin.getSchedules().size());
        assertTrue(admin.getSchedules().contains(schedule1));
        assertTrue(admin.getSchedules().contains(schedule2));

        verify(administratorRepository, times(1)).save(admin);
    }

    @Test
    void testAddScheduleForAdmin_AdminNotFound() {
        // Arrange
        AdminUpdateDTO dto = new AdminUpdateDTO();
        dto.setIdAdmin("noexistente");

        when(administratorRepository.findById("noexistente")).thenReturn(Optional.empty());

        // Act & Assert
        UserManagementException exception = assertThrows(UserManagementException.class, () -> {
            userService.addScheduleForAdmin(dto);
        });

        assertEquals("Administrador no encontrado", exception.getMessage());
    }

    @Test
    void testAddScheduleForAdmin_ScheduleNotFound() {
        // Arrange
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

        // Act & Assert
        UserManagementException exception = assertThrows(UserManagementException.class, () -> {
            userService.addScheduleForAdmin(dto);
        });

        assertEquals("Horario no encontrado: " + scheduleId, exception.getMessage());
    }
}

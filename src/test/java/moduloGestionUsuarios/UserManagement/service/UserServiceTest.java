package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.DTO.AdminRegisterDTO;
import moduloGestionUsuarios.UserManagement.exception.UserManagementException;
import moduloGestionUsuarios.UserManagement.model.Administrator;
import moduloGestionUsuarios.UserManagement.model.Role;
import moduloGestionUsuarios.UserManagement.model.Specialty;
import moduloGestionUsuarios.UserManagement.repository.AdministratorRepositoryJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private AdministratorRepositoryJPA administratorRepository;

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

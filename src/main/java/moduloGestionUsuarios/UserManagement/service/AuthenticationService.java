package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.DTO.UserLogDTO;
import moduloGestionUsuarios.UserManagement.exception.UserManagementException;
import moduloGestionUsuarios.UserManagement.model.Administrator;
import moduloGestionUsuarios.UserManagement.model.Role;
import moduloGestionUsuarios.UserManagement.model.Student;
import moduloGestionUsuarios.UserManagement.repository.AdministratorRepositoryJPA;
import moduloGestionUsuarios.UserManagement.repository.StudentRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Authentication service responsible for validating user credentials (students and administrators)
 * and generating a JWT token upon successful authentication.
 * This service implements {@link AuthenticationServiceInterface} and is annotated with {@code @Service}
 * to be managed by the Spring container. It uses dependency injection for accessing user repositories,
 * the JWT service, and the password encoder.
 */
@Service
public class AuthenticationService implements AuthenticationServiceInterface {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private StudentRepositoryJPA studentRepository;

    @Autowired
    private AdministratorRepositoryJPA administratorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Authenticates a user based on their username and password.
     * First attempts to authenticate as a student. If not found, attempts to authenticate as an administrator.
     * If the credentials are valid, a JWT token is returned.
     *
     * @param userLogDTO A data transfer object containing the username and password.
     * @return A JWT token if authentication is successful.
     * @throws UserManagementException if the user is not found or if the password is incorrect.
     */
    public String authenticate(UserLogDTO userLogDTO) throws UserManagementException {
        String userName = userLogDTO.getUserName();
        String studentEmail = userName+"@mail.escuelaing.edu.co";
        String adminEmail = userName+"@escuelaing.edu.co";
        String password = userLogDTO.getPassword();
        Optional<Student> student = studentRepository.findByEmailAddress(studentEmail);
        Optional<Administrator> administrator = administratorRepository.findByEmailAddress(adminEmail);
        if (student.isPresent()) {
            Student stud = student.get();
            return studentAunthenticate(password, userName, stud);
        }
        if(administrator.isPresent()) {
            Administrator admin = administrator.get();
            return administratorAunthenticate(password, userName, admin);
        }
        throw new UserManagementException(UserManagementException.User_Not_Found);
    }

    /**
     * Validates student credentials and generates a JWT token if the password is correct.
     *
     * @param password The password provided by the user.
     * @param userName The username of the student.
     * @param student  The corresponding {@link Student} entity.
     * @return A JWT token if authentication is successful.
     * @throws UserManagementException if the password is incorrect.
     */
    private String studentAunthenticate(String password,String userName,Student student) throws UserManagementException {
        if(passwordEncoder.matches(password,student.getStudentPassword())) {
            return jwtService.generateToken(student.getCodeStudent(),userName,student.getEmailAddress(),student.getFullName(), Role.STUDENT.name(),"null");
        }
        throw new UserManagementException(UserManagementException.Incorrect_password);
    }

    /**
     * Validates administrator credentials and generates a JWT token if the password is correct.
     *
     * @param password The password provided by the user.
     * @param userName The username of the administrator.
     * @param admin    The corresponding {@link Administrator} entity.
     * @return A JWT token if authentication is successful.
     * @throws UserManagementException if the password is incorrect.
     */
    private String administratorAunthenticate(String password,String userName,Administrator admin) throws UserManagementException {

        if(passwordEncoder.matches(password,admin.getAdminPassword())) {
            if(admin.getRole().equals(Role.DOCTOR)) {
                return jwtService.generateToken(admin.getIdAdmin(), userName, admin.getEmailAddress(), admin.getFullName(), admin.getRole().name(), admin.getSpecialty().name());
            }else{
                return jwtService.generateToken(admin.getIdAdmin(), userName, admin.getEmailAddress(), admin.getFullName(), admin.getRole().name(), "null");
            }
        }
        throw new UserManagementException(UserManagementException.Incorrect_password);
    }
}

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

    public String authenticate(UserLogDTO userLogDTO) throws UserManagementException {
        String userName = userLogDTO.getUserName();
        String studentEmail = userName+"@mail.escuelaing.edu.co";
        String adminEmail = userName+"@escuelaing.edu.co";
        String password = userLogDTO.getPassword();
        Optional<Student> student = studentRepository.findByEmailAddress(studentEmail);
        Optional<Administrator> administrator = administratorRepository.findByEmailAddress(adminEmail);
        if (student.isPresent()) {
            Student stud = student.get();
            return studentAuntenticate(password, userName, stud);
        }
        if(administrator.isPresent()) {
            Administrator admin = administrator.get();
            return administratorAuntenticate(password, userName, admin);
        }
        throw new UserManagementException(UserManagementException.User_Not_Found);
    }

    private String studentAuntenticate(String password,String userName,Student student) throws UserManagementException {
        if(passwordEncoder.matches(password,student.getStudentPassword())) {
            return jwtService.generateToken(student.getIdStudent(),userName,student.getEmailAddress(),student.getFullName(), Role.STUDENT);
        }
        throw new UserManagementException(UserManagementException.Incorrect_password);
    }

    private String administratorAuntenticate(String password,String userName,Administrator admin) throws UserManagementException {

        if(passwordEncoder.matches(password,admin.getAdminPassword())) {
            return jwtService.generateToken(admin.getIdAdmin(),userName,admin.getEmailAddress(),admin.getFullName(), admin.getRole());
        }
        throw new UserManagementException(UserManagementException.Incorrect_password);
    }
}

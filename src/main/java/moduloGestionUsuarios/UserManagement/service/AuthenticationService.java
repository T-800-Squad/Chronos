package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.DTO.UserLogDTO;
import moduloGestionUsuarios.UserManagement.UserManagementException;
import moduloGestionUsuarios.UserManagement.model.Administrator;
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
            return studentAuntenticate(password, userName, student);
        }
        if(administrator.isPresent()) {
            return administratorAuntenticate(password, userName, administrator);
        }
        throw new UserManagementException(UserManagementException.User_Not_Found);
    }

    private String studentAuntenticate(String password,String userName,Optional<Student> student) throws UserManagementException {
        Student stud = student.get();
        if(passwordEncoder.matches(password,stud.getStudentPassword())) {
            return jwtService.generateToken(userName, "student");
        }
        throw new UserManagementException(UserManagementException.Incorrect_password);
    }

    private String administratorAuntenticate(String password,String userName,Optional<Administrator> administrator) throws UserManagementException {
        Administrator admin = administrator.get();
        if(passwordEncoder.matches(password,admin.getAdminPassword())) {
            return jwtService.generateToken(userName, admin.getRole());
        }
        throw new UserManagementException(UserManagementException.Incorrect_password);
    }
}

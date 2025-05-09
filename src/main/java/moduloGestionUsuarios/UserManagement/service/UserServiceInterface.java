package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.DTO.AdminRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.StudentRegisterDTO;
<<<<<<< HEAD
import moduloGestionUsuarios.UserManagement.DTO.UserDTO;
import moduloGestionUsuarios.UserManagement.UserManagementException;
import moduloGestionUsuarios.UserManagement.model.Administrator;
import java.util.List;
=======
import moduloGestionUsuarios.UserManagement.DTO.UserUpdateDTO;
import moduloGestionUsuarios.UserManagement.exception.UserManagementException;
import moduloGestionUsuarios.UserManagement.model.*;
import org.springframework.dao.DataIntegrityViolationException;
>>>>>>> 6e39dcaf4a4606974e5be6703db8ef7c7137320b

public interface UserServiceInterface {
    void addStudent(StudentRegisterDTO student) throws UserManagementException;
    Administrator addAdministrator(AdminRegisterDTO administrator) throws UserManagementException;
    void updateStudent(UserUpdateDTO userUpdateDTO);

<<<<<<< HEAD
    List<UserDTO> queryUser(UserDTO userDTO) throws UserManagementException;
=======
    void deleteStudent(String idStudent);
>>>>>>> 6e39dcaf4a4606974e5be6703db8ef7c7137320b
}

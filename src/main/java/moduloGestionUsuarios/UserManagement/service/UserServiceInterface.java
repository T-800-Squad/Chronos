package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.DTO.AdminRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.StudentRegisterDTO;

import moduloGestionUsuarios.UserManagement.DTO.UserDTO;

import moduloGestionUsuarios.UserManagement.model.Administrator;
import java.util.List;

import moduloGestionUsuarios.UserManagement.DTO.UserUpdateDTO;
import moduloGestionUsuarios.UserManagement.exception.UserManagementException;

/**
 * Interface that defines user-related operations for students and administrators.
 * It includes methods for registering, updating, and deleting users,
 * as well as managing emergency contact data associated with students.
 */
public interface UserServiceInterface {
    void addStudent(StudentRegisterDTO student) throws UserManagementException;
    Administrator addAdministrator(AdminRegisterDTO administrator) throws UserManagementException;
    void updateStudent(UserUpdateDTO userUpdateDTO);
    void deleteStudent(String idStudent);
}

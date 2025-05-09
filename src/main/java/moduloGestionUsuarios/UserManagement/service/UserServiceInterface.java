package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.DTO.AdminRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.StudentRegisterDTO;

import moduloGestionUsuarios.UserManagement.DTO.UserDTO;

import moduloGestionUsuarios.UserManagement.model.Administrator;
import java.util.List;

import moduloGestionUsuarios.UserManagement.DTO.UserUpdateDTO;
import moduloGestionUsuarios.UserManagement.exception.UserManagementException;

public interface UserServiceInterface {
    void addStudent(StudentRegisterDTO student) throws UserManagementException;
    Administrator addAdministrator(AdminRegisterDTO administrator) throws UserManagementException;
    void updateStudent(UserUpdateDTO userUpdateDTO);

    List<UserDTO> queryUser(UserDTO userDTO) throws UserManagementException;
    void deleteStudent(String idStudent);

}

package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.DTO.AdminRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.StudentRegisterDTO;
import moduloGestionUsuarios.UserManagement.exception.UserManagementException;
import moduloGestionUsuarios.UserManagement.model.Administrator;

public interface UserServiceInterface {
    void addStudent(StudentRegisterDTO student) throws UserManagementException;
    Administrator addAdministrator(AdminRegisterDTO administrator) throws UserManagementException;
}

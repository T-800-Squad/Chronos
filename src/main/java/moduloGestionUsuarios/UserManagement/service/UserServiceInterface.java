package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.DTO.AdminRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.StudentRegisterDTO;
import moduloGestionUsuarios.UserManagement.model.Administrator;

public interface UserServiceInterface {
    void addStudent(StudentRegisterDTO student);
    Administrator addAdministrator(AdminRegisterDTO administrator);

}

package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.DTO.UserDTO;
import moduloGestionUsuarios.UserManagement.exception.UserManagementException;

import java.util.List;

public interface QueryServiceInterface {
    List<UserDTO> query(UserDTO userDTO)throws UserManagementException;
}

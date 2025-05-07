package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.DTO.UserLogDTO;
import moduloGestionUsuarios.UserManagement.exception.UserManagementException;

public interface AuthenticationServiceInterface {
    public String authenticate(UserLogDTO userLogDTO) throws UserManagementException;
}

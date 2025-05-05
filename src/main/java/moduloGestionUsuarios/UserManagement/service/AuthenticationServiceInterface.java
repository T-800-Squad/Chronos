package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.DTO.UserLogDTO;
import moduloGestionUsuarios.UserManagement.UserManagementException;

public interface AuthenticationServiceInterface {
    public String authenticate(UserLogDTO userLogDTO) throws UserManagementException;
}

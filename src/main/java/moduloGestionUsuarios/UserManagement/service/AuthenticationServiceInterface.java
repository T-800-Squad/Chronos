package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.DTO.UserLogDTO;
import moduloGestionUsuarios.UserManagement.exception.UserManagementException;

/**
 * Interface for the authentication service.
 * Defines the contract for authenticating users based on credentials and returning a JWT token.
 */
public interface AuthenticationServiceInterface {
    public String authenticate(UserLogDTO userLogDTO) throws UserManagementException;
}

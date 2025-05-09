package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.DTO.UserDTO;
import moduloGestionUsuarios.UserManagement.exception.UserManagementException;

import java.util.List;

/**
 * Interface for querying user data based on multiple optional filtering criteria.
 * Implementations should support queries using combinations of user attributes such as
 * full name, academic program, student code, role, or ID.
 */
public interface QueryServiceInterface {
    List<UserDTO> query(UserDTO userDTO)throws UserManagementException;
}

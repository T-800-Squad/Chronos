package moduloGestionUsuarios.UserManagement.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import moduloGestionUsuarios.UserManagement.DTO.AdminUpdateDTO;
import moduloGestionUsuarios.UserManagement.DTO.UserDTO;
import moduloGestionUsuarios.UserManagement.DTO.UserUpdateDTO;

import moduloGestionUsuarios.UserManagement.exception.UserManagementException;
import moduloGestionUsuarios.UserManagement.response.ApiResponse;
import moduloGestionUsuarios.UserManagement.service.QueryServiceInterface;
import moduloGestionUsuarios.UserManagement.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;

import moduloGestionUsuarios.UserManagement.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing user-related operations such as updating, deleting,
 * and querying user data.
 */
@Tag(name = "User Controller", description = "Operaciones relacionadas con usuarios (estudiantes y administradores)")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceInterface userService;
    @Autowired
    private QueryServiceInterface queryService;

    @Operation(summary = "Actualizar usuario", description = "Actualiza la información de un estudiante y su contacto de emergencia.")
    @PutMapping()
    public ResponseEntity<ApiResponse<String>> update(@RequestBody UserUpdateDTO userUpdateDTO){
        userService.updateStudent(userUpdateDTO);
        ApiResponse<String> response = new ApiResponse<String>(
                HttpStatus.OK.value(),
                "Usuario Actualizado",
                "id" + userUpdateDTO.getIdStudent()
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Actualizar administrador", description = "Actualiza los horarios de un administrador.")
    @PutMapping("/schedule")
    public ResponseEntity<ApiResponse<String>> updateAdmin(@RequestBody AdminUpdateDTO adminUpdateDTO) throws UserManagementException {
        userService.addScheduleForAdmin(adminUpdateDTO);
        ApiResponse<String> response = new ApiResponse<String>(
                HttpStatus.OK.value(),
                "Administrador Actualizado",
                "id" + adminUpdateDTO.getIdAdmin()
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar Usuario", description = "Elimina un usuario por su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable String id) throws UserManagementException{
        userService.deleteUser(id);
        ApiResponse<String> response = new ApiResponse<String>(
                HttpStatus.OK.value(),
                "Usuario Eliminado",
                "id" + id
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Consultar usuarios", description = "Consulta estudiantes o administradores con base en filtros específicos.")
    @GetMapping("/query")
    public ResponseEntity<ApiResponse<List<UserDTO>>> queryUser(@RequestBody UserDTO userDTO) throws UserManagementException {
        ApiResponse<List<UserDTO>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Consulta realizada",
                queryService.query(userDTO)
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

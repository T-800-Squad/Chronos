package moduloGestionUsuarios.UserManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import moduloGestionUsuarios.UserManagement.DTO.*;
import moduloGestionUsuarios.UserManagement.response.ApiResponse;
import moduloGestionUsuarios.UserManagement.exception.UserManagementException;
import moduloGestionUsuarios.UserManagement.service.AuthenticationServiceInterface;
import moduloGestionUsuarios.UserManagement.service.UserService;
import moduloGestionUsuarios.UserManagement.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller responsible for user authentication and registration.
 * Exposes endpoints for registering students, administrators, and authenticating users.
 */
@RestController
@RequestMapping("/authentication")
@Tag(name = "Autenticación", description = "Controlador para registrar y autenticar usuarios")
public class AuthenticationController {
    @Autowired
    private AuthenticationServiceInterface authenticationService;

    @Autowired
    private UserServiceInterface userService;

    @Operation(summary = "Registrar estudiante", description = "Registra un nuevo estudiante en el sistema")
    @PostMapping("/student")
    public ResponseEntity<ApiResponse<String>> studentRegister(@RequestBody StudentRegisterDTO studentRegisterDTO) throws UserManagementException{
        userService.addStudent(studentRegisterDTO);
        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Estudiante registrado correctamente",
                "Id: " + studentRegisterDTO.getIdStudent()
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Registrar administrador", description = "Registra un nuevo administrador en el sistema")
    @PostMapping("/admin")
    public ResponseEntity<ApiResponse<String>> adminRegister(@RequestBody AdminRegisterDTO adminRegisterDTO) throws UserManagementException{
        userService.addAdministrator(adminRegisterDTO);
        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Administrador registrado correctamente",
                "Id: " + adminRegisterDTO.getIdAdmin()
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @Operation(summary = "Iniciar sesión", description = "Autentica a un usuario y devuelve un token JWT")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LogDTO>> login(@RequestBody UserLogDTO userLogDTO) throws UserManagementException {
        LogDTO logDTO = authenticationService.authenticate(userLogDTO);
        ApiResponse<LogDTO> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Token de autenticacion",
                logDTO
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

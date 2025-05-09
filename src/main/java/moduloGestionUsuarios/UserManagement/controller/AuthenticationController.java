package moduloGestionUsuarios.UserManagement.controller;

import moduloGestionUsuarios.UserManagement.DTO.AdminRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.StudentRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.UserUpdateDTO;
import moduloGestionUsuarios.UserManagement.response.ApiResponse;
import moduloGestionUsuarios.UserManagement.DTO.UserLogDTO;
import moduloGestionUsuarios.UserManagement.exception.UserManagementException;
import moduloGestionUsuarios.UserManagement.service.AuthenticationServiceInterface;
import moduloGestionUsuarios.UserManagement.service.UserService;
import moduloGestionUsuarios.UserManagement.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    @Autowired
    private AuthenticationServiceInterface authenticationService;

    @Autowired
    private UserServiceInterface userService;

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


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody UserLogDTO userLogDTO) throws UserManagementException {
        String token = authenticationService.authenticate(userLogDTO);
        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Token de autenticacion",
                "Bearer " + token
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

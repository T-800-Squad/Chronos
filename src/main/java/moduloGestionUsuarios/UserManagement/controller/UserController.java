package moduloGestionUsuarios.UserManagement.controller;


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

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceInterface userService;
    @Autowired
    private QueryServiceInterface queryService;
    @PutMapping()
    public ResponseEntity<ApiResponse<String>> update(@RequestBody UserUpdateDTO userUpdateDTO){
        userService.updateStudent(userUpdateDTO);
        ApiResponse<String> respnse = new ApiResponse<String>(
                HttpStatus.OK.value(),
                "Usuario Eliminado",
                "id" + userUpdateDTO.getIdStudent()
        );
        return ResponseEntity.ok(respnse);
    }

    @DeleteMapping("/{idStudent}")
    public ResponseEntity<ApiResponse<String>> deleteStudent(@PathVariable String idStudent) {
        userService.deleteStudent(idStudent);
        ApiResponse<String> respnse = new ApiResponse<String>(
                HttpStatus.OK.value(),
                "Usuario Eliminado",
                "id" + idStudent
        );
        return ResponseEntity.ok(respnse);
    }

    @PostMapping("/query")
    public ResponseEntity<ApiResponse<List<UserDTO>>> queryUser(@RequestBody UserDTO userDTO) throws UserManagementException {
        ApiResponse<List<UserDTO>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Consulta realizada",
                queryService.query(userDTO)
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}

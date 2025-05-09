package moduloGestionUsuarios.UserManagement.controller;


import moduloGestionUsuarios.UserManagement.DTO.UserDTO;
import moduloGestionUsuarios.UserManagement.DTO.UserUpdateDTO;

import moduloGestionUsuarios.UserManagement.exception.UserManagementException;
import org.springframework.beans.factory.annotation.Autowired;

import moduloGestionUsuarios.UserManagement.service.UserService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PutMapping()
    public void update(@RequestBody UserUpdateDTO userUpdateDTO){
        userService.updateStudent(userUpdateDTO);
    }

    @DeleteMapping("/{idStudent}")
    public ResponseEntity<String> deleteStudent(@PathVariable String idStudent) {
        userService.deleteStudent(idStudent);
        return ResponseEntity.ok("Estudiante eliminado");
    }

    @PostMapping("/query")
    public List<UserDTO> queryUser(@RequestBody UserDTO userDTO) throws UserManagementException {
        return userService.queryUser(userDTO);
    }

}

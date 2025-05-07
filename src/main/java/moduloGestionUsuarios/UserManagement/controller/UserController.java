package moduloGestionUsuarios.UserManagement.controller;

import moduloGestionUsuarios.UserManagement.DTO.AdminRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.ChangePasswordDTO;
import moduloGestionUsuarios.UserManagement.DTO.StudentRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.UserUpdateDTO;
import moduloGestionUsuarios.UserManagement.service.UserService;
import moduloGestionUsuarios.UserManagement.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping()
    public void studentRegister(@RequestBody StudentRegisterDTO studentRegisterDTO){

    }

    @PostMapping("/admin")
    public void adminRegister(@RequestBody AdminRegisterDTO adminRegisterDTO){

    }

    @PutMapping()
    public void update(@RequestBody UserUpdateDTO userUpdateDTO){
        userService.updateStudent(userUpdateDTO);
    }

    @DeleteMapping("/{idStudent}")
    public ResponseEntity<String> deleteStudent(@PathVariable String idStudent) {
        userService.deleteStudent(idStudent);
        return ResponseEntity.ok("Estudiante eliminado");
    }

    @PutMapping("/password")
    public void changePassword(@RequestBody ChangePasswordDTO changePasswordDTO){

    }

    @GetMapping()
    public String verifyPassword(@RequestParam String password){
        return null;
    }
}

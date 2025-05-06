package moduloGestionUsuarios.UserManagement.controller;

import moduloGestionUsuarios.UserManagement.DTO.AdminRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.ChangePasswordDTO;
import moduloGestionUsuarios.UserManagement.DTO.StudentRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.UserUpdateDTO;
import moduloGestionUsuarios.UserManagement.model.Student;
import moduloGestionUsuarios.UserManagement.service.UserService;
import moduloGestionUsuarios.UserManagement.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceInterface userService;

    @PostMapping("/student")
    public void studentRegister(@RequestBody StudentRegisterDTO studentRegisterDTO){
        userService.addStudent(studentRegisterDTO);
    }

    @PostMapping("/admin")
    public void adminRegister(@RequestBody AdminRegisterDTO adminRegisterDTO){
        userService.addAdministrator(adminRegisterDTO);
    }

    @PutMapping()
    public void update(@RequestBody UserUpdateDTO userUpdateDTO){

    }

    @DeleteMapping()
    public void delete(@RequestParam String code){

    }

    @PutMapping("/password")
    public void changePassword(@RequestBody ChangePasswordDTO changePasswordDTO){

    }

    @GetMapping()
    public String verifyPassword(@RequestParam String password){
        return null;
    }
}

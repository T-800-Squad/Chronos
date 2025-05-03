package moduloGestionUsuarios.UserManagement.controller;

import moduloGestionUsuarios.UserManagement.DTO.AdminRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.ChangePasswordDTO;
import moduloGestionUsuarios.UserManagement.DTO.StudentRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.UserUpdateDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping()
    public void studentRegister(StudentRegisterDTO studentRegisterDTO){

    }

    @PostMapping("/admin")
    public void adminRegister(AdminRegisterDTO adminRegisterDTO){

    }

    @PutMapping()
    public void update(UserUpdateDTO userUpdateDTO){

    }

    @DeleteMapping()
    public void delete(String code){

    }

    @PutMapping("/password")
    public void changePassword(ChangePasswordDTO changePasswordDTO){

    }

    @GetMapping()
    public String verifyPassword(String password){
        return null;
    }
}

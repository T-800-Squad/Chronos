package moduloGestionUsuarios.UserManagement.controller;

import moduloGestionUsuarios.UserManagement.DTO.AdminRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.ChangePasswordDTO;
import moduloGestionUsuarios.UserManagement.DTO.IdentificationDTO;
import moduloGestionUsuarios.UserManagement.DTO.StudentRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.UserDTO;
import moduloGestionUsuarios.UserManagement.DTO.UserUpdateDTO;
import moduloGestionUsuarios.UserManagement.UserManagementException;
import moduloGestionUsuarios.UserManagement.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

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

    @PostMapping("/query")
    public List<UserDTO> queryUser(@RequestBody UserDTO userDTO) throws UserManagementException {
        return userService.queryUser(userDTO);
    }

}

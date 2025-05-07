package moduloGestionUsuarios.UserManagement.controller;

import moduloGestionUsuarios.UserManagement.DTO.IdentificationDTO;
import moduloGestionUsuarios.UserManagement.DTO.UserDTO;
import moduloGestionUsuarios.UserManagement.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/query")
public class QueryController {

    private final UserService userService;

    @Autowired
    public QueryController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/name")
    public UserDTO consultByName(@RequestParam String name) {
        return userService.consultByName(name);
    }

    @GetMapping("/program")
    public UserDTO consultByAcademicProgram(@RequestParam String program) {
        return userService.consultByAcademicProgram(program);
    }

    @GetMapping("/code")
    public UserDTO consultByCode(@RequestParam String code) {
        return userService.consultByCode(code);
    }

    @GetMapping("/role")
    public UserDTO consultByRole(@RequestParam String role) {
        return userService.consultByRole(role);
    }

    @GetMapping("/identification")
    public UserDTO consultByIdentification(@RequestBody IdentificationDTO identificationDTO) {
        return userService.consultByIdentification(identificationDTO);
    }
}

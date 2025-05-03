package moduloGestionUsuarios.UserManagement.controller;

import moduloGestionUsuarios.UserManagement.DTO.IdentificationDTO;
import moduloGestionUsuarios.UserManagement.DTO.UserDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/query")
public class QueryController {

    @GetMapping("/name")
    public UserDTO consultByName(@RequestParam String name) {
        return null;
    }

    @GetMapping("/program")
    public UserDTO consultByAcademicProgram(@RequestParam String program) {
        return null;
    }

    @GetMapping("/code")
    public UserDTO consultByCode(@RequestParam String code) {
        return null;
    }

    @GetMapping("/role")
    public UserDTO consultByRole(@RequestParam String role) {
        return null;
    }

    @GetMapping("/identification")
    public UserDTO consultByIdentification(@RequestBody IdentificationDTO identificationDTO) {
        return null;
    }
}

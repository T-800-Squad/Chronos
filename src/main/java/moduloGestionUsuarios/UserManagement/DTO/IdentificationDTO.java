package moduloGestionUsuarios.UserManagement.DTO;

/**
 * Data Transfer Object (DTO) class used to encapsulate the identification data of a student.
 * This DTO contains a single field, `idStudent`, which represents the unique identifier of a student.
 * This DTO is typically used for passing student identification data between different layers of the application.
 */
public class IdentificationDTO {
    private String idStudent; 

    public IdentificationDTO(String idStudent) {
        this.idStudent = idStudent;
    }

    public String getIdStudent() {
        return idStudent;
    }
    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }
}

package moduloGestionUsuarios.UserManagement.DTO;

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

package moduloGestionUsuarios.UserManagement.DTO;

public class UserDTO {
    private String fullName;
    private String academicProgram;
    private String codeStudent;
    private String role;
    private String idStudent;

    public UserDTO(String fullName, String academicProgram, String codeStudent, String role, String idStudent) {
        this.fullName = fullName;
        this.academicProgram = academicProgram;
        this.codeStudent = codeStudent;
        this.role = role;
        this.idStudent = idStudent;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAcademicProgram() {
        return academicProgram;
    }

    public void setAcademicProgram(String academicProgram) {
        this.academicProgram = academicProgram;
    }

    public String getCodeStudent() {
        return codeStudent;
    }

    public void setCodeStudent(String codeStudent) {
        this.codeStudent = codeStudent;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIdStudent() { 
        return idStudent; 
    }

    public void setIdStudent(String idStudent) { 
        this.idStudent = idStudent; 
    }
}

package moduloGestionUsuarios.UserManagement.DTO;

import moduloGestionUsuarios.UserManagement.model.Administrator;
import moduloGestionUsuarios.UserManagement.model.Student;

/**
 * Data Transfer Object (DTO) class used to represent a user with basic details.
 * This class is used for transferring user data (e.g., administrator or student) across the application.
 */
public class UserDTO {
    private String fullName;
    private String academicProgram;
    private String codeStudent;
    private String role;
    private String id;

    public UserDTO(String fullName, String academicProgram, String codeStudent, String role, String id) {
        this.fullName = fullName;
        this.academicProgram = academicProgram;
        this.codeStudent = codeStudent;
        this.role = role;
        this.id = id;
    }

    /**
     * Compares the current instance with the specified object to determine equality.
     * This method supports comparison with objects of type {@code Administrator} or {@code Student}.
     * The comparison is performed by matching relevant attributes, ensuring that
     * two instances represent the same entity.
     *
     * @param object The object to be compared with the current instance.
     * @return {@code true} if the object is of a supported type and has identical attributes;
     *         {@code false} otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof Administrator a) {
            return a.getFullName().equals(fullName) && a.getRole().equals(role) && a.getIdAdmin().equals(id);
        }
        if (object instanceof Student s) {
            return s.getFullName().equals(fullName) && s.getAcademicProgram().equals(academicProgram) && s.getCodeStudent().equals(codeStudent) && s.getIdStudent().equals(id);
        }
        return false;
    }

    // Getters and Setters
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
    public String getId() { 
        return id; 
    }
    public void setId(String id) { 
        this.id = id; 
    }
}

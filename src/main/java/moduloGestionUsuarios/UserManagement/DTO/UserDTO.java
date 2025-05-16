package moduloGestionUsuarios.UserManagement.DTO;

import moduloGestionUsuarios.UserManagement.model.Administrator;
import moduloGestionUsuarios.UserManagement.model.Student;

/**
 * Data Transfer Object (DTO) class used to represent a user with basic details.
 * This class is used for transferring user data (e.g., administrator or student) across the application.
 */
public class UserDTO {
    private String academicProgram;
    private String codeStudent;
    private String userName;
    private String fullName;
    private String role;
    private String id;

    public UserDTO(String fullName, String academicProgram, String codeStudent, String role, String id) {
        this.fullName = fullName;
        this.academicProgram = academicProgram;
        this.codeStudent = codeStudent;
        this.role = role;
        this.id = id;
    }


    // Getters and Setters

    public String getUserName() {return userName;}
    public void setUserName(String userName) {this.userName = userName;}
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

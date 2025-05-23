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

    private String contactNumber;
    private String address;
    private String typeIdStudent;

    private String idContact;
    private String typeIdContact;
    private String fullNameContact;
    private String phoneNumber;
    private String relationship;

    public UserDTO(String fullName, String academicProgram, String codeStudent, String role, String id,
                   String userName, String contactNumber, String address, String typeIdStudent,
                   String idContact, String typeIdContact, String fullNameContact, String phoneNumber, String relationship) {
        this.fullName = fullName;
        this.academicProgram = academicProgram;
        this.codeStudent = codeStudent;
        this.role = role;
        this.id = id;
        this.userName = userName;
        this.contactNumber = contactNumber;
        this.address = address;
        this.typeIdStudent = typeIdStudent;
        this.idContact = idContact;
        this.typeIdContact = typeIdContact;
        this.fullNameContact = fullNameContact;
        this.phoneNumber = phoneNumber;
        this.relationship = relationship;
    }

    public UserDTO(String fullName, String academicProgram, String codeStudent, String role, String id) {
        this.fullName = fullName;
        this.academicProgram = academicProgram;
        this.codeStudent = codeStudent;
        this.role = role;
        this.id = id;
    }

    public UserDTO() {}

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
    public String getAddress(){return address;}
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTypeIdStudent() {
        return typeIdStudent;
    }

    public void setTypeIdStudent(String typeIdStudent) {
        this.typeIdStudent = typeIdStudent;
    }

    public String getIdContact() {
        return idContact;
    }

    public void setIdContact(String idContact) {
        this.idContact = idContact;
    }

    public String getTypeIdContact() {
        return typeIdContact;
    }

    public void setTypeIdContact(String typeIdContact) {
        this.typeIdContact = typeIdContact;
    }

    public String getFullNameContact() {
        return fullNameContact;
    }

    public void setFullNameContact(String fullNameContact) {
        this.fullNameContact = fullNameContact;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

}

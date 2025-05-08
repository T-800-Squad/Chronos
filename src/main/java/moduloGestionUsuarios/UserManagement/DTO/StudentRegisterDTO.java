package moduloGestionUsuarios.UserManagement.DTO;

import java.time.LocalDate;

public class StudentRegisterDTO {
    private String codeStudent;
    private String studentPassword;
    private String fullNameStudent;
    private String academicProgram;
    private String emailAddress;
    private String contactNumber;
    private LocalDate birthDate;
    private String address;
    private String typeIdStudent;
    private String idStudent;

    //Data EmergencyContact
    private String idContact;
    private String typeIdContact;
    private String fullNameContact;
    private String phoneNumber;
    private String relationship;

    //Getters and Setters Student
    public String getCodeStudent() { return codeStudent; }
    public void setCodeStudent(String codeStudent) { this.codeStudent = codeStudent; }
    public String getStudentPassword() { return studentPassword; }
    public void setStudentPassword(String studentPassword) { this.studentPassword = studentPassword; }
    public String getFullNameStudent() { return fullNameStudent; }
    public void setFullNameStudent(String fullNameStudent) { this.fullNameStudent = fullNameStudent; }
    public String getAcademicProgram() { return academicProgram; }
    public void setAcademicProgram(String academicProgram) { this.academicProgram = academicProgram; }
    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress;}
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) {this.contactNumber = contactNumber; }
    public LocalDate getBirthDate() { return birthDate;}
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public String getAddress() { return address; }
    public void setAddress(String address) {this.address = address;}
    public String getTypeIdStudent() { return typeIdStudent; }
    public void setTypeIdStudent(String typeIdStudent) { this.typeIdStudent = typeIdStudent; }
    public String getIdStudent() { return idStudent; }
    public void setIdStudent(String idStudent) { this.idStudent = idStudent;}

    //Getters and Setters EmergencyContact
    public String getIdContact() {return idContact;}
    public void setIdContact(String idContact) { this.idContact = idContact;}
    public String getTypeIdContact() { return typeIdContact; }
    public void setTypeIdContact(String typeIdContact) { this.typeIdContact = typeIdContact; }
    public String getFullNameContact() { return fullNameContact; }
    public void setFullNameContact(String fullNameContact) { this.fullNameContact = fullNameContact; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getRelationship() { return relationship; }
    public void setRelationship(String relationship) { this.relationship = relationship; }
}

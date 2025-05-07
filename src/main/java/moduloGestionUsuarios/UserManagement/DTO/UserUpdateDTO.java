package moduloGestionUsuarios.UserManagement.DTO;

import java.time.LocalDate;

public class UserUpdateDTO {


    private String academicProgram;
    private String contactNumber;
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

    public String getAcademicProgram() { return academicProgram; }
    public void setAcademicProgram(String academicProgram) { this.academicProgram = academicProgram; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) {this.contactNumber = contactNumber; }
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

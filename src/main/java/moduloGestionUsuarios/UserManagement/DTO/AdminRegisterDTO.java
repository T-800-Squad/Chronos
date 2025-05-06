package moduloGestionUsuarios.UserManagement.DTO;

import java.util.List;

public class AdminRegisterDTO {
    // Data Administrator
    private String idAdmin;
    private String typeId;
    private String fullName;
    private String specialty;
    private String role;
    private String emailAddress;
    private String contactNumber;
    private String adminPassword;

    // Data Schedule
    private List<String> schedule;

    //Getters and Setters Administrator
    public String getIdAdmin() {return idAdmin;}
    public void setIdAdmin(String idAdmin) {this.idAdmin = idAdmin;}
    public String getTypeId() {return typeId;}
    public void setTypeId(String typeId) {this.typeId = typeId;}
    public String getFullName() {return fullName;}
    public void setFullName(String fullName) {this.fullName = fullName;}
    public String getSpecialty() {return specialty;}
    public void setSpecialty(String specialty) {this.specialty = specialty;}
    public String getRole() {return role;}
    public void setRole(String role) {this.role = role;}
    public String getEmailAddress() {return emailAddress;}
    public void setEmailAddress(String emailAddress) {this.emailAddress = emailAddress;}
    public String getContactNumber() {return contactNumber;}
    public void setContactNumber(String contactNumber) {this.contactNumber = contactNumber;}
    public String getAdminPassword() {return adminPassword;}
    public void setAdminPassword(String adminPassword) {this.adminPassword = adminPassword;}

    //Getters and Setters Schedule
    public List<String> getSchedule() {return schedule;}
    public void setSchedule(List<String> schedule) {this.schedule = schedule;}
}

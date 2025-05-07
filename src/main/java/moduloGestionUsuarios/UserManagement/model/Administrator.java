package moduloGestionUsuarios.UserManagement.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="administrator")
public class Administrator {

    @Column(name="id_admin")
    @Id
    private String idAdmin;

    @Column(name="type_id")
    private String typeId;

    @Column(name ="full_name")
    private String fullName;

    private String specialty;

    @Column(name="email_address")
    private String emailAddress;

    @Column(name="contact_number")
    private String contactNumber;

    @Column(name="admin_password")
    private String adminPassword;

    @Column(name = "role")
    private String role;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "schedule_admin",
            joinColumns = @JoinColumn(name = "id_admin"),
            inverseJoinColumns = @JoinColumn(name = "id_schedule")
    )
    private List<Schedule> schedules = new ArrayList<>();

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getIdAdmin() {
        return idAdmin;
    }
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
    public String getTypeId() {
        return typeId;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getFullName() {
        return fullName;
    }
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
    public String getSpecialty() {
        return specialty;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public String getEmailAddress() {
        return emailAddress;
    }
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    public String getContactNumber() {
        return contactNumber;
    }
    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
    public String getAdminPassword() {
        return adminPassword;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
    public List<Schedule> getSchedules() {return schedules;}
    public void setSchedules(List<Schedule> schedules) {this.schedules = schedules;}
}

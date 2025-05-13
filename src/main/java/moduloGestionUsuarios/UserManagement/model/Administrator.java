package moduloGestionUsuarios.UserManagement.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Administrator entity in the system.
 * This class maps to the {@code administrator} table in the database and contains
 * information about an administrator such as their ID, type of ID, full name,
 * specialty, email address, contact number, password, role, and associated schedules.
 * Administrators may have different roles and may be linked to one or more schedules.
 */
@Entity
@Table(name="administrator")
public class Administrator {

    /**
     * The unique identifier of the administrator.
     * This is the primary key and is mapped to the {@code id_admin} column in the database.
     */
    @Column(name="id_admin")
    @Id
    private String idAdmin;

    /**
     * The type of ID of the administrator.
     * This is mapped to the {@code type_id} column in the database.
     */
    @Column(name="type_id")
    private String typeId;

    /**
     * The full name of the administrator.
     * This is mapped to the {@code full_name} column in the database.
     */
    @Column(name ="full_name")
    private String fullName;

    /**
     * The specialty of the administrator, applicable if their role is related to a medical field.
     * This is mapped to the {@code specialty} column in the database.
     */
    @Column(name="specialty")
    private Specialty specialty;

    /**
     * The email address of the administrator.
     * This is mapped to the {@code email_address} column in the database.
     */
    @Column(name="email_address")
    private String emailAddress;

    /**
     * The contact number of the administrator.
     * This is mapped to the {@code contact_number} column in the database.
     */
    @Column(name="contact_number")
    private String contactNumber;

    /**
     * The administrator's password.
     * This is mapped to the {@code admin_password} column in the database.
     */
    @Column(name="admin_password")
    private String adminPassword;

    /**
     * The role of the administrator (e.g., Doctor, Medical Secretary, etc.).
     * This is mapped to the {@code role} column in the database.
     */
    @Column(name = "role")
    private Role role;

    /**
     * A list of schedules associated with the administrator.
     * This is a many-to-many relationship with the {@link Schedule} entity.
     * The relationship is represented by the {@code schedule_admin} join table.
     */
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "schedule_admin",
            joinColumns = @JoinColumn(name = "id_admin"),
            inverseJoinColumns = @JoinColumn(name = "id_schedule")
    )
    private List<Schedule> schedules = new ArrayList<>();;

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
    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }
    public Specialty getSpecialty() {
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
    public void setRole(Role role) {
        this.role = role;
    }
    public Role getRole() {
        return role;
    }
    public void setSchedules(List<Schedule> schedules) {this.schedules = schedules;}
    public List<Schedule> getSchedules() {return schedules;}
}
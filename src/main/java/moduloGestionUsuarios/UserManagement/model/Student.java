package moduloGestionUsuarios.UserManagement.model;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a student.
 * This entity stores the details of a student, including personal information, academic program, and emergency contacts.
 * It is mapped to the "student" table in the database.
 */
@Entity
@Table(name="student")
public class Student {

    /**
     * The unique identifier of the student.
     * This is the primary key and is mapped to the {@code id_student} column in the database.
     */
    @Column(name = "id_student")
    @Id
    private String idStudent;

    /**
     * The code associated with the student.
     * This is mapped to the {@code code_student} column in the database.
     */
    @Column(name = "code_student")
    private String codeStudent;

    /** The type of identification for the student.
     *  This is mapped to the {@code type_id} column in the database.
     */
    @Column(name="type_id")
    private String typeId;

    /** The full name of the student.
     *  This is mapped to the {@code full_name} column in the database.
     */
    @Column(name="full_name")
    private String fullName;

    /**
     * The academic program the student is enrolled in.
     * This is mapped to the {@code academic_program} column in the database.
     */
    @Column(name="academic_program")
    private String academicProgram;

    /**
     * The student's email address.
     * This is mapped to the {@code email_address} column in the database.
     */
    @Column(name="email_address")
    private String emailAddress;

    /**
     * The student's contact number.
     * This is mapped to the {@code contact_number} column in the database.
     */
    @Column(name="contact_number")
    private String contactNumber;

    private LocalDate birthdate;

    private String address;

    /**
     * The student's password for authentication.
     * This is mapped to the {@code student_password} column in the database.
     */
    @Column(name="student_password")
    private String studentPassword;

    /**
     * The list of emergency contacts associated with the student.
     * This is mapped to the {@code student_contact} table via a many-to-many relationship.
     */
    @ManyToMany()
    @JoinTable(
            name = "student_contact",
            joinColumns = @JoinColumn(name = "id_student"),
            inverseJoinColumns = @JoinColumn(name = "id_contact")
    )
    private List<EmergencyContact> emergencyContacts = new ArrayList<>();

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }
    public String getIdStudent() {
        return idStudent;
    }
    public void setCodeStudent(String codeStudent) {
        this.codeStudent = codeStudent;
    }
    public String getCodeStudent() {
        return codeStudent;
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
    public void setAcademicProgram(String academicProgram) {
        this.academicProgram = academicProgram;
    }
    public String getAcademicProgram() {
        return academicProgram;
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
    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
    public LocalDate getBirthdate() {
        return birthdate;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }
    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }
    public String getStudentPassword() {
        return studentPassword;
    }
    public List<EmergencyContact> getEmergencyContacts() {
        return emergencyContacts;
    }
    public void setId(String number) {
        this.idStudent = number;
    }
    // Suponiendo que el "userName" es el mismo que el email
    public String getUserName() {
        return this.emailAddress;
    }

    // Obtener datos del primer contacto de emergencia, si existe
    public String getIdContact() {
        if (!emergencyContacts.isEmpty()) {
            return emergencyContacts.get(0).getIdContact();
        }
        return null;
    }

    public String getTypeIdContact() {
        if (!emergencyContacts.isEmpty()) {
            return emergencyContacts.get(0).getTypeId();
        }
        return null;
    }

    public String getFullNameContact() {
        if (!emergencyContacts.isEmpty()) {
            return emergencyContacts.get(0).getFullName();
        }
        return null;
    }

    public String getPhoneNumber() {
        if (!emergencyContacts.isEmpty()) {
            return emergencyContacts.get(0).getPhoneNumber();
        }
        return null;
    }

    public String getRelationship() {
        if (!emergencyContacts.isEmpty()) {
            return emergencyContacts.get(0).getRelationship();
        }
        return null;
    }

    public String getTypeIdStudent() {
        return this.typeId;
    }

}
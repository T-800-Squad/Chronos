package moduloGestionUsuarios.UserManagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;


@Entity
@Table(name="student")
public class Student {

    @Column(name = "id_student")
    @Id
    private String idStudent;

    @Column(name = "code_student")
    private String codeStudent;

    @Column(name="type_id")
    private String typeId;

    @Column(name="full_name")
    private String fullName;

    @Column(name="academic_program")
    private String academicProgram;

    @Column(name="email_address")
    private String emailAddress;

    @Column(name="contact_number")
    private String contactNumber;

    private LocalDate birthdate;

    private String address;

    @Column(name="student_password")
    private String studentPassword;

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

}


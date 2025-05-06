package moduloGestionUsuarios.UserManagement.model;

import jakarta.persistence.*;

@Entity
@Table(name="student_contact")
@IdClass(StudentContactId.class)
public class StudentContact {

    @Id
    private String code_student;

    @Id
    private String id_contact;

    public String getCode_student() {
        return code_student;
    }
    public void setCode_student(String code_student) {
        this.code_student = code_student;
    }
    public String getId_contact() {
        return id_contact;
    }
}

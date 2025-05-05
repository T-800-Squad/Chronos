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
}

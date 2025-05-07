package moduloGestionUsuarios.UserManagement.model;



import java.io.Serializable;


public class StudentContactId implements Serializable {

    private String code_student;

    private String id_contact;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StudentContactId studentContactId) {
            if(studentContactId.code_student == this.code_student && studentContactId.id_contact == this.id_contact){
                return true;
            }
        }
        return false;
    }
    @Override
    public int hashCode() {
        return code_student.hashCode() + id_contact.hashCode();
    }
}

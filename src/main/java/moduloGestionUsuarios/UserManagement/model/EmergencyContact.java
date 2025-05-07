package moduloGestionUsuarios.UserManagement.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity

@Table(name="emergency_contact")
public class EmergencyContact {

    @Column(name="id_contact")
    @Id
    private String idContact;

    @Column(name="full_name")
    private String fullName;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="type_id")
    private String typeId;

    @Column(name="relationship")
    private String relationship;

    @ManyToMany(mappedBy = "emergencyContacts")
    private List<Student> students = new ArrayList<>();

    public String getIdContact() {
        return idContact;
    }
    public void setIdContact(String idContact) {
        this.idContact = idContact;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getTypeId() {
        return typeId;
    }
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
    public String getRelationship() {
        return relationship;
    }
    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public List<Student> getStudents() {return students;}


    public void setId(String number) {
        this.idContact = number;
    }

}


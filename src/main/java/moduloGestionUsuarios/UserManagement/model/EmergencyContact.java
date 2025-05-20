package moduloGestionUsuarios.UserManagement.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an emergency contact for a student.
 * This class maps to the {@code emergency_contact} table in the database and contains
 * details about an emergency contact, including their ID, full name, phone number,
 * relationship with the student, and the associated students.
 * A student can have one or more emergency contacts.
 */
@Entity
@Table(name="emergency_contact")
public class EmergencyContact {

    /**
     * The unique identifier of the emergency contact.
     * This is the primary key and is mapped to the {@code id_contact} column in the database.
     */
    @Column(name="id_contact")
    @Id
    private String idContact;

    /**
     * The full name of the emergency contact.
     * This is mapped to the {@code full_name} column in the database.
     */
    @Column(name="full_name")
    private String fullName;

    /**
     * The phone number of the emergency contact.
     * This is mapped to the {@code phone_number} column in the database.
     */
    @Column(name="phone_number")
    private String phoneNumber;

    /**
     * The type of ID for the emergency contact (e.g., national ID, passport, etc.).
     * This is mapped to the {@code type_id} column in the database.
     */
    @Column(name="type_id")
    private String typeId;

    /**
     * The relationship of the emergency contact to the student (e.g., parent, sibling, friend).
     * This is mapped to the {@code relationship} column in the database.
     */
    @Column(name="relationship")
    private String relationship;

    /**
     * A list of students associated with this emergency contact.
     * This is a many-to-many relationship with the {@link Student} entity.
     * The relationship is mapped by the {@code emergencyContacts} field in the {@link Student} entity.
     */
    @ManyToMany(mappedBy = "emergencyContacts")
    private List<Student> students = new ArrayList<>();

    public String getIdContact() {return idContact;}
    public void setIdContact(String idContact) {this.idContact = idContact;}
    public String getFullName() {return fullName;}
    public void setFullName(String fullName) {this.fullName = fullName;}
    public String getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
    public String getTypeId() {return typeId;}
    public void setTypeId(String typeId) {this.typeId = typeId;}
    public String getRelationship() {return relationship;}
    public void setRelationship(String relationship) {this.relationship = relationship;}
    public List<Student> getStudents() {return students;}
    public void setId(String number) {this.idContact = number;}
}

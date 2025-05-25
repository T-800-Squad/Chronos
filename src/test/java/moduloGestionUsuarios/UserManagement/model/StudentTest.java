package moduloGestionUsuarios.UserManagement.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentTest {

    @Test
    void testBasicSettersAndGetters() {
        Student student = new Student();
        student.setIdStudent("S001");
        student.setCodeStudent("C123");
        student.setTypeId("CC");
        student.setFullName("Juan Pérez");
        student.setAcademicProgram("Ingeniería de Sistemas");
        student.setEmailAddress("juan@example.com");
        student.setContactNumber("3112345678");
        student.setBirthdate(LocalDate.of(2000, 1, 15));
        student.setAddress("Cra 1 #2-34");
        student.setStudentPassword("password123");

        assertEquals("S001", student.getIdStudent());
        assertEquals("C123", student.getCodeStudent());
        assertEquals("CC", student.getTypeId());
        assertEquals("Juan Pérez", student.getFullName());
        assertEquals("Ingeniería de Sistemas", student.getAcademicProgram());
        assertEquals("juan@example.com", student.getEmailAddress());
        assertEquals("3112345678", student.getContactNumber());
        assertEquals(LocalDate.of(2000, 1, 15), student.getBirthdate());
        assertEquals("Cra 1 #2-34", student.getAddress());
        assertEquals("password123", student.getStudentPassword());
    }

    @Test
    void testGetUserName() {
        Student student = new Student();
        student.setEmailAddress("test@correo.com");

        assertEquals("test@correo.com", student.getUserName());
    }

    @Test
    void testEmergencyContactMethodsWithOneContact() {
        EmergencyContact contact = new EmergencyContact();
        contact.setIdContact("C1");
        contact.setTypeId("TI");
        contact.setFullName("Ana Gómez");
        contact.setPhoneNumber("3009876543");
        contact.setRelationship("Hermana");

        Student student = new Student();
        student.getEmergencyContacts().add(contact);

        assertEquals("C1", student.getIdContact());
        assertEquals("TI", student.getTypeIdContact());
        assertEquals("Ana Gómez", student.getFullNameContact());
        assertEquals("3009876543", student.getPhoneNumber());
        assertEquals("Hermana", student.getRelationship());
    }

    @Test
    void testEmergencyContactMethodsWhenEmpty() {
        Student student = new Student();

        assertNull(student.getIdContact());
        assertNull(student.getTypeIdContact());
        assertNull(student.getFullNameContact());
        assertNull(student.getPhoneNumber());
        assertNull(student.getRelationship());
    }

    @Test
    void testSetIdAlias() {
        Student student = new Student();
        student.setId("S123");

        assertEquals("S123", student.getIdStudent());
    }

    @Test
    void testGetTypeIdStudent() {
        Student student = new Student();
        student.setTypeId("CC");

        assertEquals("CC", student.getTypeIdStudent());
    }
}

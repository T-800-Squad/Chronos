package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.DTO.UserUpdateDTO;
import moduloGestionUsuarios.UserManagement.model.EmergencyContact;
import moduloGestionUsuarios.UserManagement.model.Student;
import moduloGestionUsuarios.UserManagement.repository.AdministratorRepositoryJPA;
import moduloGestionUsuarios.UserManagement.repository.EmergencyContactRepositoryJPA;
import moduloGestionUsuarios.UserManagement.repository.StudentRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceInterface {
    @Autowired
    private StudentRepositoryJPA studentRepository;

    @Autowired
    private AdministratorRepositoryJPA administratorRepository;

    @Autowired
    private EmergencyContactRepositoryJPA emergencyContactRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void updateStudent(UserUpdateDTO userUpdateDTO) {
        Student student = studentRepository.findById(userUpdateDTO.getIdStudent())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        student.setAddress(userUpdateDTO.getAddress());
        student.setAcademicProgram(userUpdateDTO.getAcademicProgram());
        student.setContactNumber(userUpdateDTO.getContactNumber());
        student.setTypeId(userUpdateDTO.getTypeIdStudent());

        EmergencyContact emergencyContact = emergencyContactRepository.findById(userUpdateDTO.getIdContact())
                .orElseThrow(() -> new RuntimeException("Contacto de emergencia no encontrado"));

        emergencyContact.setFullName(userUpdateDTO.getFullNameContact());
        emergencyContact.setTypeId(userUpdateDTO.getTypeIdContact());
        emergencyContact.setPhoneNumber(userUpdateDTO.getPhoneNumber());
        emergencyContact.setRelationship(userUpdateDTO.getRelationship());

        studentRepository.save(student);
        emergencyContactRepository.save(emergencyContact);
    }


    public void addEmergencyContact(Student student, EmergencyContact emergencyContact){
        student.getEmergencyContacts().add(emergencyContact);
    }

    public void deleteStudent(String idStudent) {
        studentRepository.deleteById(idStudent);
    }

}

package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.DTO.StudentRegisterDTO;
import moduloGestionUsuarios.UserManagement.model.Administrator;
import moduloGestionUsuarios.UserManagement.model.EmergencyContact;
import moduloGestionUsuarios.UserManagement.model.Student;
import moduloGestionUsuarios.UserManagement.repository.AdministratorRepositoryJPA;
import moduloGestionUsuarios.UserManagement.repository.EmergencyContactRepositoryJPA;
import moduloGestionUsuarios.UserManagement.repository.StudentRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public void addStudent(StudentRegisterDTO studentRegisterDTO){
        Student student = new Student();

        student.setIdStudent(studentRegisterDTO.getIdStudent());
        student.setCodeStudent(studentRegisterDTO.getCodeStudent());
        student.setStudentPassword(passwordEncoder.encode(studentRegisterDTO.getStudentPassword()));
        student.setAddress(studentRegisterDTO.getAddress());
        student.setBirthdate(studentRegisterDTO.getBirthDate());
        student.setAcademicProgram(studentRegisterDTO.getAcademicProgram());
        student.setContactNumber(studentRegisterDTO.getContactNumber());
        student.setEmailAddress(studentRegisterDTO.getEmailAddress());
        student.setTypeId(studentRegisterDTO.getTypeIdStudent());
        student.setFullName(studentRegisterDTO.getFullNameStudent());

        EmergencyContact emergencyContact = new EmergencyContact();
        emergencyContact.setIdContact(studentRegisterDTO.getIdContact());
        emergencyContact.setFullName(studentRegisterDTO.getFullNameContact());
        emergencyContact.setTypeId(studentRegisterDTO.getTypeIdContact());
        emergencyContact.setPhoneNumber(studentRegisterDTO.getPhoneNumber());
        emergencyContact.setRelationship(studentRegisterDTO.getRelationship());
        emergencyContactRepository.save(emergencyContact);

        studentRepository.save(student);
    }

    public Administrator addAdministrator(Administrator administrator){
        Administrator newAdministrator = new Administrator();

        newAdministrator.setContactNumber(administrator.getContactNumber());
        newAdministrator.setEmailAddress(administrator.getEmailAddress());
        newAdministrator.setFullName(administrator.getFullName());
        newAdministrator.setTypeId(administrator.getTypeId());
        newAdministrator.setRole(administrator.getRole());
        newAdministrator.setSpecialty(administrator.getSpecialty());
        newAdministrator.setIdAdmin(administrator.getIdAdmin());
        newAdministrator.setAdminPassword(passwordEncoder.encode(administrator.getAdminPassword()));

        return administratorRepository.save(newAdministrator);
    }

    public Optional<Administrator> findByEmailAddressAdmin(String email){
        return administratorRepository.findByEmailAddress(email);
    }

    public Optional<Student> findByEmailAddressStudent(String email){
        return studentRepository.findByEmailAddress(email);
    }
}

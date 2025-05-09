package moduloGestionUsuarios.UserManagement.service;


import moduloGestionUsuarios.UserManagement.DTO.AdminRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.StudentRegisterDTO;
import moduloGestionUsuarios.UserManagement.exception.UserManagementException;
import moduloGestionUsuarios.UserManagement.model.*;
import moduloGestionUsuarios.UserManagement.repository.AdministratorRepositoryJPA;
import moduloGestionUsuarios.UserManagement.repository.EmergencyContactRepositoryJPA;
import moduloGestionUsuarios.UserManagement.repository.ScheduleRepository;
import moduloGestionUsuarios.UserManagement.DTO.UserUpdateDTO;
import moduloGestionUsuarios.UserManagement.repository.StudentRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
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

    private ScheduleRepository scheduleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void addStudent(StudentRegisterDTO studentRegisterDTO) throws UserManagementException {
        if (studentRepository.findByEmailAddress(studentRegisterDTO.getEmailAddress()).isPresent()) {
            throw new UserManagementException("Ya existe un estudiante con ese correo electrónico");
        }

        if (studentRepository.findById(studentRegisterDTO.getIdStudent()).isPresent()) {
            throw new UserManagementException("Ya existe un estudiante con ese ID");
        }Student student = new Student();

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

        studentRepository.saveAndFlush(student);
        emergencyContactRepository.save(emergencyContact);
        student.getEmergencyContacts().add(emergencyContact);
        studentRepository.save(student);
    }

    public Administrator addAdministrator(AdminRegisterDTO adminRegisterDTO) throws UserManagementException {
        if (administratorRepository.findByEmailAddress(adminRegisterDTO.getEmailAddress()).isPresent()) {
            throw new UserManagementException("Ya existe un administrador con ese correo electrónico");
        }

        if (administratorRepository.findById(adminRegisterDTO.getIdAdmin()).isPresent()) {
            throw new UserManagementException("Ya existe un administrador con ese ID");
        }
        Administrator administrator = new Administrator();

        administrator.setContactNumber(adminRegisterDTO.getContactNumber());
        administrator.setEmailAddress(adminRegisterDTO.getEmailAddress());
        administrator.setFullName(adminRegisterDTO.getFullName());
        administrator.setTypeId(adminRegisterDTO.getTypeId());
        administrator.setIdAdmin(adminRegisterDTO.getIdAdmin());
        administrator.setAdminPassword(passwordEncoder.encode(adminRegisterDTO.getAdminPassword()));

        administrator.setRole(Role.valueOf(adminRegisterDTO.getRole().toUpperCase()));
        administrator.setSpecialty(Specialty.valueOf(adminRegisterDTO.getSpecialty().toUpperCase()));

        List<Schedule> scheduleList = new ArrayList<>();
        for (Integer scheduleId : adminRegisterDTO.getSchedule()) {
            scheduleRepository.findById(scheduleId).ifPresent(scheduleList::add);
        }
        administrator.setSchedules(scheduleList);

        return administratorRepository.save(administrator);
    }

    public Optional<Student> findByEmailAddressStudent(String email){
        return studentRepository.findByEmailAddress(email);
    }

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

    public void deleteStudent(String idStudent) {
        studentRepository.deleteById(idStudent);
    }
}

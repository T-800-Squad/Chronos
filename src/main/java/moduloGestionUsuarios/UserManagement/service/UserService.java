package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.DTO.AdminRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.IdentificationDTO;
import moduloGestionUsuarios.UserManagement.DTO.StudentRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.UserDTO;
import moduloGestionUsuarios.UserManagement.model.Administrator;
import moduloGestionUsuarios.UserManagement.model.EmergencyContact;
import moduloGestionUsuarios.UserManagement.model.Schedule;
import moduloGestionUsuarios.UserManagement.model.Student;
import moduloGestionUsuarios.UserManagement.repository.AdministratorRepositoryJPA;
import moduloGestionUsuarios.UserManagement.repository.EmergencyContactRepositoryJPA;
import moduloGestionUsuarios.UserManagement.repository.ScheduleRepository;
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

        studentRepository.saveAndFlush(student);
        emergencyContactRepository.save(emergencyContact);
        student.getEmergencyContacts().add(emergencyContact);
        studentRepository.save(student);
    }

    public Administrator addAdministrator(AdminRegisterDTO adminRegisterDTO){
        Administrator administrator = new Administrator();

        administrator.setContactNumber(adminRegisterDTO.getContactNumber());
        administrator.setEmailAddress(adminRegisterDTO.getEmailAddress());
        administrator.setFullName(adminRegisterDTO.getFullName());
        administrator.setTypeId(adminRegisterDTO.getTypeId());
        administrator.setRole(adminRegisterDTO.getRole());
        administrator.setSpecialty(adminRegisterDTO.getSpecialty());
        administrator.setIdAdmin(adminRegisterDTO.getIdAdmin());
        administrator.setAdminPassword(passwordEncoder.encode(adminRegisterDTO.getAdminPassword()));

        List<Schedule> scheduleList = new ArrayList<>();
        for (Integer scheduleId : adminRegisterDTO.getSchedule()) {
            scheduleRepository.findById(String.valueOf(scheduleId)).ifPresent(scheduleList::add);
        }
        administrator.setSchedules(scheduleList);

        return administratorRepository.save(administrator);
    }

    public Optional<Administrator> findByEmailAddressAdmin(String email){
        return administratorRepository.findByEmailAddress(email);
    }

    public Optional<Student> findByEmailAddressStudent(String email){
        return studentRepository.findByEmailAddress(email);
    }

    public UserDTO consultByName(String name) {
    return studentRepository.findByFullName(name)
            .map(this::convertToDTO)
            .orElse(null);
    }

    public UserDTO consultByAcademicProgram(String program) {
        return studentRepository.findByAcademicProgram(program)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public UserDTO consultByCode(String code) {
        return studentRepository.findByCodeStudent(code)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public UserDTO consultByRole(String role) {
        return administratorRepository.findByRole(role)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public UserDTO consultByIdentification(IdentificationDTO identificationDTO) {
        return studentRepository.findByIdStudent(identificationDTO.getIdStudent())
                .map(this::convertToDTO)
                .orElse(null);
    }
    
    private UserDTO convertToDTO(Student student) {
        return new UserDTO(student.getFullName(), student.getAcademicProgram(), student.getCodeStudent(), "Student");
    }

    private UserDTO convertToDTO(Administrator admin) {
        return new UserDTO(admin.getFullName(), admin.getSpecialty(), admin.getIdAdmin(), admin.getRole());
    }
}

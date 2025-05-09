package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.DTO.AdminRegisterDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public UserDTO queryUser(UserDTO userDTO) {
        if (userDTO.getFullName() != null) {
            return studentRepository.findByFullName(userDTO.getFullName())
                    .map(this::convertToDTO)
                    .orElse(null);
        }
        if (userDTO.getAcademicProgram() != null) {
            return studentRepository.findByAcademicProgram(userDTO.getAcademicProgram())
                    .map(this::convertToDTO)
                    .orElse(null);
        }
        if (userDTO.getCodeStudent() != null) {
            return studentRepository.findByCodeStudent(userDTO.getCodeStudent())
                    .map(this::convertToDTO)
                    .orElse(null);
        }
        if (userDTO.getRole() != null) {
            return administratorRepository.findByRole(userDTO.getRole())
                    .map(this::convertToDTO)
                    .orElse(null);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No valid search parameters provided");
    }

    private UserDTO convertToDTO(Student student) {
        return new UserDTO(student.getFullName(), student.getAcademicProgram(), student.getCodeStudent(), "Student", student.getIdStudent());
    }
    
    private UserDTO convertToDTO(Administrator admin) {
        return new UserDTO(admin.getFullName(), admin.getSpecialty(), admin.getIdAdmin(), admin.getRole(), null);
    }
    
    
}

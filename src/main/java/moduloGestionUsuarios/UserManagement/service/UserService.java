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
import org.springframework.dao.DataIntegrityViolationException;
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

    private ScheduleRepository scheduleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void addStudent(StudentRegisterDTO studentRegisterDTO) throws UserManagementException {
        try {
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
        } catch (DataIntegrityViolationException e){
            throw new UserManagementException("Violación de integridad de datos: " + e.getMostSpecificCause().getMessage());
        } catch (Exception e) {
            throw new UserManagementException("Error inesperado: " + e.getMessage());
        }
    }

    public Administrator addAdministrator(AdminRegisterDTO adminRegisterDTO) throws UserManagementException {
        try {
            Administrator administrator = new Administrator();

            administrator.setContactNumber(adminRegisterDTO.getContactNumber());
            administrator.setEmailAddress(adminRegisterDTO.getEmailAddress());
            administrator.setFullName(adminRegisterDTO.getFullName());
            administrator.setTypeId(adminRegisterDTO.getTypeId());
            administrator.setIdAdmin(adminRegisterDTO.getIdAdmin());
            administrator.setAdminPassword(passwordEncoder.encode(adminRegisterDTO.getAdminPassword()));

            administrator.setRole(Role.valueOf(adminRegisterDTO.getRole().toUpperCase()));

            Role role = Role.valueOf(adminRegisterDTO.getRole().toUpperCase());
            administrator.setRole(role);

            if (role == Role.DOCTOR) {
                String specialtyStr = adminRegisterDTO.getSpecialty();
                if (specialtyStr == null || specialtyStr.trim().isEmpty()) {
                    throw new UserManagementException("La especialidad es obligatoria para el rol DOCTOR.");
                }
                administrator.setSpecialty(Specialty.valueOf(specialtyStr.toUpperCase()));
            } else {
                administrator.setSpecialty(null);
            }

            return administratorRepository.save(administrator);
        } catch (DataIntegrityViolationException e){
            throw new UserManagementException("Violación de integridad de datos: " + e.getMostSpecificCause().getMessage());
        } catch (Exception e) {
            throw new UserManagementException("Error inesperado: " + e.getMessage());
        }
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

package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.UserManagementException;
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
import org.apache.catalina.User;
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
    
    public List<UserDTO> queryUser(UserDTO userDTO) throws UserManagementException{
        List<UserDTO> userlist = new ArrayList<>();
        
        if (userDTO.getFullName() != null) {
            List<Student> users = studentRepository.findByFullName(userDTO.getFullName());
            List<Administrator> admins = administratorRepository.findFullname(userDTO.getFullName());
            userlist.addAll(userNameNotNull(users,admins,userlist));
        }
        if (userDTO.getAcademicProgram() != null) {
            List<Student> students = studentRepository.findByAcademicProgram(userDTO.getAcademicProgram());
            userlist = academicProgramAndCodeStudentNotNull(students,userlist);
        }
        if (userDTO.getCodeStudent() != null) {
            List<Student> students = studentRepository.findByCodeStudent(userDTO.getCodeStudent());
            userlist = academicProgramAndCodeStudentNotNull(students,userlist);
        }
        if (userDTO.getRole() != null) {
            List<Administrator> admins = administratorRepository.findByRole(userDTO.getRole());
            userlist = roleNotNull(admins,userlist);
        }
        if (userDTO.getId() != null) {
            List<Student> students = studentRepository.findByIdStudent(userDTO.getId());
            List<Administrator> admins = administratorRepository.findByIdAdmin(userDTO.getId());
            userlist = academicProgramAndCodeStudentNotNull(students,userlist);
            userlist = roleNotNull(admins,userlist);
        }
        return userlist;
    }

    private List<UserDTO> userNameNotNull(List<Student> users, List<Administrator> admins,List<UserDTO> userlist) throws UserManagementException{
        if (users.isEmpty() && admins.isEmpty()) {
            throw new UserManagementException(UserManagementException.User_Not_Exist);
        }
        for (Administrator administrator : admins) {
            userlist.add(convertToDTO(administrator));
        }
        for (Student user : users) {
            userlist.add(convertToDTO(user));
        }
        return userlist;
    }

    private List<UserDTO> academicProgramAndCodeStudentNotNull(List<Student> students, List<UserDTO> userlist)throws UserManagementException{
        List<UserDTO> programs = new ArrayList<>();
        if (students.isEmpty()) {
            throw new UserManagementException(UserManagementException.User_Not_Exist);
        }
        if (!userlist.isEmpty()) {
            for (UserDTO u : userlist) {
                for (Student s : students) {
                    if (u.equals(s)) {
                        programs.add(u);
                    }
                }
            }
        }
        if (programs.isEmpty()) {
            throw new UserManagementException(UserManagementException.User_Not_Exist);
        }
        return programs;
    }

    private List<UserDTO> roleNotNull(List<Administrator> admins, List<UserDTO> userlist)throws UserManagementException{
        List<UserDTO> programs = new ArrayList<>();
        if (admins.isEmpty()) {
            throw new UserManagementException(UserManagementException.User_Not_Exist);
        }
        if (!userlist.isEmpty()) {
            for (UserDTO u : userlist) {
                for (Administrator s : admins) {
                    if (u.equals(s)) {
                        programs.add(u);
                    }
                }
            }
        }
        if (programs.isEmpty()) {
            throw new UserManagementException(UserManagementException.User_Not_Exist);
        }
        return programs;
    }
    private UserDTO convertToDTO(Student student) {
        return new UserDTO(student.getFullName(), student.getAcademicProgram(), student.getCodeStudent(), "Student", student.getIdStudent());
    }
    
    private UserDTO convertToDTO(Administrator admin) {
        return new UserDTO(admin.getFullName(), admin.getSpecialty(), admin.getIdAdmin(), admin.getRole(), null);
    }
    
    
}

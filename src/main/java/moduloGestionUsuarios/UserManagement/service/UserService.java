package moduloGestionUsuarios.UserManagement.service;


import moduloGestionUsuarios.UserManagement.DTO.AdminRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.StudentRegisterDTO;
import moduloGestionUsuarios.UserManagement.DTO.UserDTO;
import moduloGestionUsuarios.UserManagement.model.Administrator;
import moduloGestionUsuarios.UserManagement.model.EmergencyContact;

import moduloGestionUsuarios.UserManagement.model.Student;



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

import org.springframework.dao.DataIntegrityViolationException;


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
        return new UserDTO(admin.getFullName(), admin.getSpecialty().getDescription(), admin.getIdAdmin(), admin.getRole().getDescription(), null);
    }
    
    
}

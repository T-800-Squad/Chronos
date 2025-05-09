package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.DTO.UserDTO;
import moduloGestionUsuarios.UserManagement.exception.UserManagementException;
import moduloGestionUsuarios.UserManagement.model.Administrator;
import moduloGestionUsuarios.UserManagement.model.Role;
import moduloGestionUsuarios.UserManagement.model.Student;
import moduloGestionUsuarios.UserManagement.repository.AdministratorRepositoryJPA;
import moduloGestionUsuarios.UserManagement.repository.StudentRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueryService implements QueryServiceInterface{

    @Autowired
    private StudentRepositoryJPA studentRepository;

    @Autowired
    private AdministratorRepositoryJPA administratorRepository;
    public List<UserDTO> query(UserDTO userDTO) throws UserManagementException{
        List<UserDTO> userlist = new ArrayList<>();

        if (userDTO.getFullName() != null) {
            List<Student> users = studentRepository.findByFullName(userDTO.getFullName());
            List<Administrator> admins = administratorRepository.findByFullName(userDTO.getFullName());
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
            List<Administrator> admins = administratorRepository.findByRole(Role.fromDescription(userDTO.getRole()));
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

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

/**
 * Service that handles complex user queries across both students and administrators.
 * It supports filtering users by full name, academic program, student code, role, or ID,
 * and returns matching results as {@link UserDTO} objects.
 */
@Service
public class QueryService implements QueryServiceInterface{

    @Autowired
    private StudentRepositoryJPA studentRepository;

    @Autowired
    private AdministratorRepositoryJPA administratorRepository;

    /**
     * Performs user queries based on fields provided in a {@link UserDTO} object.
     * Supports filtering by full name, academic program, student code, role, or ID.
     *
     * @param userDTO A DTO containing the filtering criteria.
     * @return A list of users matching the query.
     * @throws UserManagementException If no matching users are found for a given criterion.
     */
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

    /**
     * Filters and adds users and administrators with matching full names.
     *
     * @param users     List of matching students.
     * @param admins    List of matching administrators.
     * @param userlist  List where matches will be added.
     * @return A list with added matching users.
     * @throws UserManagementException If no matches are found.
     */
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

    /**
     * Filters students by academic program or code if the initial user list is not empty.
     *
     * @param students  List of students matching the current filter.
     * @param userlist  Current list of filtered users.
     * @return A filtered list of users matching both previous and current criteria.
     * @throws UserManagementException If no students match or the filter yields no overlap.
     */
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

    /**
     * Filters administrators by role if the initial user list is not empty.
     *
     * @param admins    List of administrators matching the role.
     * @param userlist  Current list of filtered users.
     * @return A filtered list of users matching both previous and current criteria.
     * @throws UserManagementException If no administrators match or the filter yields no overlap.
     */
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

    /**
     * Converts a {@link Student} entity into a {@link UserDTO}.
     *
     * @param student The student to convert.
     * @return A DTO containing student information.
     */
    private UserDTO convertToDTO(Student student) {
        return new UserDTO(student.getFullName(), student.getAcademicProgram(), student.getCodeStudent(), "Student", student.getIdStudent());
    }

    /**
     * Converts an {@link Administrator} entity into a {@link UserDTO}.
     *
     * @param admin The administrator to convert.
     * @return A DTO containing administrator information.
     */
    private UserDTO convertToDTO(Administrator admin) {
        return new UserDTO(admin.getFullName(), admin.getSpecialty().getDescription(), admin.getIdAdmin(), admin.getRole().getDescription(), null);
    }
}

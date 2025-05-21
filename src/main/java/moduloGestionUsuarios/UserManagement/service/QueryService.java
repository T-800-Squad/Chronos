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
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<UserDTO> query(UserDTO userDTO) throws UserManagementException {
        List<UserDTO> userDTOList = new ArrayList<>();
        String academicProgram = userDTO.getAcademicProgram();
        String codeStudent = userDTO.getCodeStudent();
        String userName = userDTO.getUserName();
        String fullName = userDTO.getFullName();
        String role = userDTO.getRole();
        String id = userDTO.getId();

        if(academicProgram != null){
            List<Student> students = studentRepository.findByAcademicProgram(academicProgram);
            userDTOList = convertStudentsToDTO(students);
            validateUserListNotEmpty(userDTOList);
        }
        if(codeStudent != null){
            Optional<Student> student = studentRepository.findByCodeStudent(codeStudent);
            validateStudentOptionalNotEmpty(student);
            userDTOList.add(convertToDTO(student.get()));
        }
        if(userName != null){
            Optional<Student> optionalStudent = studentRepository.findByEmailAddress((userName+"@mail.escuelaing.edu.co"));
            Optional<Administrator> administratorOptional = administratorRepository.findByEmailAddress((userName+"@escuelaing.edu.co"));
            validateOptionalsNotEmpty(userDTOList,optionalStudent, administratorOptional);
        }
        if(fullName != null){
            userDTOList = findByFullName(fullName);
        }
        if(role != null){
            if("STUDENT".equals(role)){
                List<Student> students = studentRepository.findAll();
                userDTOList = convertStudentsToDTO(students);
            }else{
                String stringRole = role.toUpperCase();
                List<Administrator> administrators = administratorRepository.findByRole(Role.valueOf(stringRole));
                userDTOList = convertAdministratorsToDTO(administrators);
            }
        }
        if(id != null){
            Optional<Student> student = studentRepository.findByIdStudent(id);
            Optional<Administrator> administrator = administratorRepository.findByIdAdmin(id);
            validateOptionalsNotEmpty(userDTOList,student,administrator);
        }
        return userDTOList;

    }

    private List<UserDTO> findByFullName(String fullName) throws UserManagementException {
        List<Student> students = studentRepository.findByFullName(fullName);
        List<Administrator> administrators = administratorRepository.findByFullName(fullName);
        return joinUserList(students,administrators);
    }


    private List<UserDTO> joinUserList(List<Student> students, List<Administrator> administrators) throws UserManagementException {
        List<UserDTO> userDTOList;
        List<UserDTO> studentDTOList = convertStudentsToDTO(students);
        List<UserDTO> administratorDTOList = convertAdministratorsToDTO(administrators);
        validateUsersListNotEmpty(studentDTOList,administratorDTOList);
        userDTOList = studentDTOList;
        userDTOList.addAll(administratorDTOList);
        return userDTOList;
    }

    private List<UserDTO> convertStudentsToDTO(List<Student> students) throws UserManagementException {
        List<UserDTO> userDTOList = new ArrayList<>();
        for(Student student : students){
            userDTOList.add(convertToDTO(student));
        }
        return userDTOList;
    }
    private List<UserDTO> convertAdministratorsToDTO(List<Administrator> administrators) throws UserManagementException {
        List<UserDTO> userDTOList = new ArrayList<>();
        for(Administrator administrator : administrators){
            userDTOList.add(convertToDTO(administrator));
        }
        return userDTOList;
    }


    private void validateUserListNotEmpty(List<UserDTO> userDTOList) throws UserManagementException {
        if(userDTOList.isEmpty()){
            throw new UserManagementException(UserManagementException.User_Not_Exist);
        }
    }
    private void validateUsersListNotEmpty(List<UserDTO> studentDTOList, List<UserDTO> administratorDTOList) throws UserManagementException {
        if(studentDTOList.isEmpty() && administratorDTOList.isEmpty()){
            throw new UserManagementException(UserManagementException.User_Not_Exist);
        }
    }


    private void validateStudentOptionalNotEmpty(Optional<Student> studentOptional) throws UserManagementException {
        if(studentOptional.isEmpty()){
            throw new UserManagementException(UserManagementException.User_Not_Exist);
        }
    }

    private void validateOptionalsNotEmpty(List<UserDTO> userDTOList ,Optional<Student> studentOptional, Optional<Administrator> administratorOptional) throws UserManagementException {
        if(studentOptional.isEmpty() && administratorOptional.isEmpty()){
            throw new UserManagementException(UserManagementException.User_Not_Exist);
        }
        studentOptional.ifPresent(student -> userDTOList.add(convertToDTO(student)));
        administratorOptional.ifPresent(administrator -> userDTOList.add(convertToDTO(administrator)));
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
        return new UserDTO(admin.getFullName(),null,null, admin.getRole().name(), admin.getIdAdmin());
    }
}

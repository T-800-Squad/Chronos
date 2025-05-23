package moduloGestionUsuarios.UserManagement.service;


import jakarta.transaction.Transactional;
import moduloGestionUsuarios.UserManagement.DTO.*;
import moduloGestionUsuarios.UserManagement.model.Administrator;
import moduloGestionUsuarios.UserManagement.model.EmergencyContact;
import moduloGestionUsuarios.UserManagement.model.Student;
import moduloGestionUsuarios.UserManagement.exception.UserManagementException;
import moduloGestionUsuarios.UserManagement.model.*;
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

import org.springframework.dao.DataIntegrityViolationException;

/**
 * Service that handles user operations such as registering, updating, and deleting students and administrators.
 * This includes managing relationships with emergency contacts and ensuring data integrity during operations.
 */
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


    /**
     * Registers a new student along with their emergency contact information.
     *
     * @param studentRegisterDTO DTO containing the student and emergency contact data.
     * @throws UserManagementException If a data integrity violation occurs or another unexpected error happens.
     */
    public void addStudent(StudentRegisterDTO studentRegisterDTO) throws UserManagementException {
        if(studentRepository.existsById(studentRegisterDTO.getIdStudent())){
            throw new UserManagementException(UserManagementException.Student_Exist);
        }
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

    /**
     * Registers a new administrator.
     * If the administrator's role is DOCTOR, a specialty must also be provided.
     *
     * @param adminRegisterDTO DTO containing administrator registration data.
     * @return The saved {@link Administrator} entity.
     * @throws UserManagementException If required fields are missing or a data error occurs.
     */
    public Administrator addAdministrator(AdminRegisterDTO adminRegisterDTO) throws UserManagementException {
        if(administratorRepository.existsById(adminRegisterDTO.getIdAdmin())){
            throw new UserManagementException(UserManagementException.Admin_Exist);
        }
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

            List<Schedule> scheduleList = new ArrayList<>();
            for (Integer scheduleId : adminRegisterDTO.getSchedule()) {
                scheduleRepository.findById(scheduleId).ifPresent(scheduleList::add);
            }
            administrator.setSchedules(scheduleList);

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

    /**
     * Updates student and emergency contact information.
     *
     * @param userUpdateDTO DTO containing updated student and contact information.
     */
    public void updateStudent(UserUpdateDTO userUpdateDTO) {
        // Buscar estudiante
        Student student = studentRepository.findById(userUpdateDTO.getIdStudent())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        // Actualizar solo si el campo no es nulo
        if (userUpdateDTO.getAddress() != null) {
            student.setAddress(userUpdateDTO.getAddress());
        }
        if (userUpdateDTO.getAcademicProgram() != null) {
            student.setAcademicProgram(userUpdateDTO.getAcademicProgram());
        }
        if (userUpdateDTO.getContactNumber() != null) {
            student.setContactNumber(userUpdateDTO.getContactNumber());
        }
        if (userUpdateDTO.getTypeIdStudent() != null) {
            student.setTypeId(userUpdateDTO.getTypeIdStudent());
        }

        if (userUpdateDTO.getIdContact() != null) {

            // Buscar contacto de emergencia
            EmergencyContact emergencyContact = emergencyContactRepository.findById(userUpdateDTO.getIdContact())
                    .orElseThrow(() -> new RuntimeException("Contacto de emergencia no encontrado"));

            // Actualizar solo si el campo no es nulo
            if (userUpdateDTO.getFullNameContact() != null) {
                emergencyContact.setFullName(userUpdateDTO.getFullNameContact());
            }
            if (userUpdateDTO.getTypeIdContact() != null) {
                emergencyContact.setTypeId(userUpdateDTO.getTypeIdContact());
            }
            if (userUpdateDTO.getPhoneNumber() != null) {
                emergencyContact.setPhoneNumber(userUpdateDTO.getPhoneNumber());
            }
            if (userUpdateDTO.getRelationship() != null) {
                emergencyContact.setRelationship(userUpdateDTO.getRelationship());
            }

            emergencyContactRepository.save(emergencyContact);
        }
        // Guardar los cambios
        studentRepository.save(student);

    }

    /**
     * Updates admin with a new time slot.
     *
     * @param adminUpdateDTO DTO containing updated admin and new time slot.
     */
    @Transactional
    public void addScheduleForAdmin(AdminUpdateDTO adminUpdateDTO) throws UserManagementException {
        Administrator administrator = administratorRepository.findById(adminUpdateDTO.getIdAdmin())
                .orElseThrow(() -> new UserManagementException("Administrador no encontrado"));
        List<Integer> newScheduleIds = adminUpdateDTO.getNewSchedule();
        if (newScheduleIds != null) {
            for (Integer scheduleId : newScheduleIds) {
                Schedule schedule = scheduleRepository.findById(scheduleId)
                        .orElseThrow(() -> new UserManagementException("Horario no encontrado: " + scheduleId));

                if (!administrator.getSchedules().contains(schedule)) {
                    administrator.getSchedules().add(schedule);
                }
            }
        } else {
            System.out.println(">>> La lista de nuevos horarios es null.");
        }
        administratorRepository.save(administrator);
    }

    /**
     * Deletes a student by ID.
     *
     * @param id The ID of the student or administrator to delete.
     */
    @Transactional
    public void deleteUser(String id) throws UserManagementException {
        boolean eliminado = false;

        if (studentRepository.existsByIdStudent(id)) {
            studentRepository.deleteByIdStudent(id);
            eliminado = true;
        }

        if (administratorRepository.existsById(id)) {
            administratorRepository.deleteById(id);
            eliminado = true;
        }

        if (!eliminado) {
            throw new UserManagementException("No se encontró ningún usuario con el ID: " + id);
        }
    }
    public void updatePassword(UpdatePasswordDTO updatePasswordDTO) throws UserManagementException {
        Optional<Student> optionalStudent = studentRepository.findByCodeStudent(updatePasswordDTO.getId());
        if(optionalStudent.isPresent()) {
            updateStudentPassword(optionalStudent, updatePasswordDTO);
            return;
        }
        Optional<Administrator> optionalAdministrator = administratorRepository.findByIdAdmin(updatePasswordDTO.getId());
        if(optionalAdministrator.isPresent()) {
            updateAdminPassword(optionalAdministrator, updatePasswordDTO);
        }
    }

    private void updateStudentPassword(Optional<Student> optionalStudent, UpdatePasswordDTO updatePasswordDTO) throws UserManagementException {
        Student student = optionalStudent.get();
        String password = student.getStudentPassword();
        validatePassword(password, updatePasswordDTO);
        String newPassword = passwordEncoder.encode(updatePasswordDTO.getNewPassword());
        student.setStudentPassword(newPassword);
        studentRepository.save(student);
    }

    private void updateAdminPassword(Optional<Administrator> optionalAdministrator, UpdatePasswordDTO updatePasswordDTO) throws UserManagementException {
        Administrator administrator = optionalAdministrator.get();
        String password = administrator.getAdminPassword();
        validatePassword(password, updatePasswordDTO);
        String newPassword = passwordEncoder.encode(updatePasswordDTO.getNewPassword());
        administrator.setAdminPassword(newPassword);
        administratorRepository.save(administrator);
    }

    private void validatePassword(String password,UpdatePasswordDTO updatePasswordDTO) throws UserManagementException {
        if(!passwordEncoder.matches(updatePasswordDTO.getPassword(),password)){
            throw new UserManagementException(UserManagementException.Incorrect_password);
        }
        if(!updatePasswordDTO.getNewPassword().equals(updatePasswordDTO.getNewPasswordConfirm())) {
            throw new UserManagementException(UserManagementException.Different_Password);
        }
        if(passwordEncoder.matches(updatePasswordDTO.getNewPassword(),password)) {
            throw new UserManagementException(UserManagementException.Same_Password);
        }
    }

}

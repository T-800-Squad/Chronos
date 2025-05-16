package moduloGestionUsuarios.UserManagement.repository;

import moduloGestionUsuarios.UserManagement.model.Student;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Student} entities.
 * This interface extends {@link JpaRepository} and provides custom query methods to retrieve
 * {@link Student} entities based on different attributes such as email address, full name, academic program,
 * student code, and student ID.
 */
@Repository
public interface StudentRepositoryJPA extends JpaRepository<Student,String> {
    Optional<Student> findByEmailAddress(String email);
    List<Student> findByFullName(String fullName);
    List<Student> findByAcademicProgram(String academicProgram);
    Optional<Student> findByCodeStudent(String codeStudent);
    Optional<Student> findByIdStudent(String idStudent);
    void deleteByIdStudent(String idStudent);
    List<Student> findByAcademicProgramAndCodeStudent(String academicProgram,String codeStudent);
}

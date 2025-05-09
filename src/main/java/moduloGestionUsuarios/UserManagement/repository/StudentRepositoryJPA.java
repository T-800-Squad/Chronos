package moduloGestionUsuarios.UserManagement.repository;

import moduloGestionUsuarios.UserManagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepositoryJPA extends JpaRepository<Student,String> {
    Optional<Student> findByEmailAddress(String email);
    List<Student> findByFullName(String fullName);
    List<Student> findByAcademicProgram(String academicProgram);
    List<Student> findByCodeStudent(String codeStudent);
    List<Student> findByIdStudent(String idStudent);
}

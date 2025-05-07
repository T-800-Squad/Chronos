package moduloGestionUsuarios.UserManagement.repository;

<<<<<<< HEAD

=======
>>>>>>> development
import moduloGestionUsuarios.UserManagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepositoryJPA extends JpaRepository<Student,String> {
<<<<<<< HEAD
}
=======
    Optional<Student> findByEmailAddress(String email);

}
>>>>>>> development

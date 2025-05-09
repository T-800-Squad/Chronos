package moduloGestionUsuarios.UserManagement.repository;

import moduloGestionUsuarios.UserManagement.model.Administrator;
import moduloGestionUsuarios.UserManagement.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdministratorRepositoryJPA extends JpaRepository<Administrator, String> {
    Optional<Administrator> findByEmailAddress(String email);
    List<Administrator> findByRole(Role role);
    List<Administrator> findByFullName(String fullname);
    List<Administrator> findByIdAdmin(String idAdmin);
}

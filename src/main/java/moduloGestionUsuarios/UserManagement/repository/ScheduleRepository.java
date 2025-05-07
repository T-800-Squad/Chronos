package moduloGestionUsuarios.UserManagement.repository;

import moduloGestionUsuarios.UserManagement.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {
}

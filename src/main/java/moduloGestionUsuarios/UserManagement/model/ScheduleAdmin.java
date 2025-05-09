package moduloGestionUsuarios.UserManagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name="schedule_admin")
@IdClass(ScheduleAdminId.class)
public class ScheduleAdmin {

    @Id
    private String id_admin;

    @Id
    private Integer id_schedule;

    public String getId_admin() {
        return id_admin;
    }
    public void setId_admin(String id_admin) {
        this.id_admin = id_admin;
    }
    public Integer getId_schedule() {
        return id_schedule;
    }
    public void setId_schedule(Integer id_schedule) {
        this.id_schedule = id_schedule;
    }
}

package moduloGestionUsuarios.UserManagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

/**
 * Represents the many-to-many relationship between administrators and schedules.
 * This entity serves as the join table that links administrators to their respective schedules.
 * The primary key is a composite of `id_admin` (the identifier of the administrator) and `id_schedule` (the identifier of the schedule).
 */
@Entity
@Table(name="schedule_admin")
@IdClass(ScheduleAdminId.class)
public class ScheduleAdmin {

    /** The identifier of the administrator. This is part of the composite primary key. */
    @Id
    private String id_admin;

    /** The identifier of the schedule. This is part of the composite primary key. */
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

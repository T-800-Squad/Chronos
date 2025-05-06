package moduloGestionUsuarios.UserManagement.model;

import java.io.Serializable;

public class ScheduleAdminId implements Serializable {
    private String id_admin;
    private String id_schedule;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ScheduleAdminId scheduleAdminId) {
            if(scheduleAdminId.id_admin.equals(id_admin) && scheduleAdminId.id_schedule.equals(id_schedule)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id_admin.hashCode() + id_schedule.hashCode();
    }
}

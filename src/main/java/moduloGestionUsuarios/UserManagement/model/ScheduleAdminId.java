package moduloGestionUsuarios.UserManagement.model;

import java.io.Serializable;


/**
 * Represents the composite primary key for the {@link ScheduleAdmin} entity.
 * This class is used to represent the composite key consisting of `id_admin` (the identifier of the administrator)
 * and `id_schedule` (the identifier of the schedule). It is necessary for the proper functioning of the many-to-many
 * relationship between administrators and schedules.
 */
public class ScheduleAdminId implements Serializable {
    private String id_admin;
    private String id_schedule;

    /**
     * Compares this ScheduleAdminId with another object to check if they are equal.
     * Two ScheduleAdminId objects are considered equal if they have the same `id_admin` and `id_schedule` values.
     *
     * @param obj the object to compare this ScheduleAdminId with
     * @return {@code true} if the two objects are equal, otherwise {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ScheduleAdminId scheduleAdminId) {
            if (scheduleAdminId.id_admin.equals(id_admin) && scheduleAdminId.id_schedule.equals(id_schedule)){
                return true;
            }
        }
        return false;
    }

    /**
     * Generates a hash code for this ScheduleAdminId.
     * The hash code is computed based on the `id_admin` and `id_schedule` values.
     *
     * @return the hash code for this ScheduleAdminId
     */
    @Override
    public int hashCode() {
        return id_admin.hashCode() + id_schedule.hashCode();
    }
}

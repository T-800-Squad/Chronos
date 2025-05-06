package moduloGestionUsuarios.UserManagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="schedule")
public class Schedule {

    @Column(name="id_schedule")
    @Id
    private String idSchedule;

    @Column(name="day_of_week")
    private String dayOfWeek;

    @Column(name="time_slot")
    private String timeSlot;

    public void setIdSchedule(String idSchedule) {
        this.idSchedule = idSchedule;
    }

    public String getIdSchedule() {
        return idSchedule;
    }
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    public String getDayOfWeek() {
        return dayOfWeek;
    }
    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }
    public String getTimeSlot() {
        return timeSlot;
    }
}

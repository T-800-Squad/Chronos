package moduloGestionUsuarios.UserManagement.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the schedule of an administrator or user within the system.
 * The schedule contains details about the day of the week and the time slot when a user is scheduled to perform their duties.
 * This entity is linked to administrators who are assigned to various schedules.
 */
@Entity
@Table(name="schedule")
public class Schedule {

    /** The unique identifier for the schedule. */
    @Column(name="id_schedule")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSchedule;

    /** The day of the week when the schedule takes place. */
    @Column(name="day_of_week")
    private String dayOfWeek;

    /** The time slot assigned to the schedule. */
    @Column(name="time_slot")
    private String timeSlot;

    /** The administrators associated with this schedule. */
    @ManyToMany(mappedBy = "schedules")
    private List<Administrator> administrators = new ArrayList<>();

    public void setIdSchedule(Integer idSchedule) {
        this.idSchedule = idSchedule;
    }
    public Integer getIdSchedule() {
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
    public void setAdministrators(List<Administrator> administrators) {this.administrators = administrators;}
    public List<Administrator> getAdministrators() { return administrators; }
}

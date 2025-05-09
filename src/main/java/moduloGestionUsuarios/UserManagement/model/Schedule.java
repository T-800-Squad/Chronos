package moduloGestionUsuarios.UserManagement.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="schedule")
public class Schedule {

    @Column(name="id_schedule")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSchedule;

    @Column(name="day_of_week")
    private String dayOfWeek;

    @Column(name="time_slot")
    private String timeSlot;

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

package moduloGestionUsuarios.UserManagement.DTO;

import java.util.List;

public class AdminUpdateDTO {
    private String idAdmin;
    private List<Integer> newSchedule;

    public void setIdAdmin(String idAdmin) {this.idAdmin = idAdmin;}
    public void setNewSchedule(List<Integer> newSchedule) {this.newSchedule = newSchedule;}
    public String getIdAdmin() {return idAdmin;}
    public List<Integer> getNewSchedule() {return newSchedule;}
}

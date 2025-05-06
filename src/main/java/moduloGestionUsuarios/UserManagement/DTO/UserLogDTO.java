package moduloGestionUsuarios.UserManagement.DTO;

public class UserLogDTO {
    private String userName;
    private String password;

    public UserLogDTO(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }

}

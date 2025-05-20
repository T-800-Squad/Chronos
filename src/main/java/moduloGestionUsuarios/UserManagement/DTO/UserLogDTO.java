package moduloGestionUsuarios.UserManagement.DTO;

/**
 * Data Transfer Object (DTO) class used for capturing user login credentials.
 * This class is typically used during the authentication process to receive input from the user.
 */
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

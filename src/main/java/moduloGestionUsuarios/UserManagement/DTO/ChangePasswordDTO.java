package moduloGestionUsuarios.UserManagement.DTO;

/**
 * Data Transfer Object (DTO) class used to encapsulate the data required for changing a user's password.
 * This DTO contains two fields: the new password that the user wants to set and the confirmation of that password.
 * These fields ensure that the user enters the correct new password and confirms it before the change is applied.
 */
public class ChangePasswordDTO {
    private String newPassword;
    private String confirmPassword;

    public String getNewPassword() {
        return newPassword;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
}

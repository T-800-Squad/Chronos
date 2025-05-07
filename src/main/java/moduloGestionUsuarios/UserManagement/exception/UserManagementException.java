package moduloGestionUsuarios.UserManagement.exception;

public class UserManagementException extends Exception {

    public static String User_Not_Found = "El nombre de usuario no fue encontrado";
    public static String Incorrect_password = "la contraseña es incorrecta";

    public UserManagementException(String message) {
        super(message);
    }
}

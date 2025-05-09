package moduloGestionUsuarios.UserManagement.exception;

public class UserManagementException extends Exception {

    public static String User_Not_Found = "El nombre de usuario no fue encontrado";
    public static String Incorrect_password = "la contraseña es incorrecta";
    public static final String INVALID_SCHEDULE = "El horario asignado es incorrecto";
    public static final String USER_ALREADY_EXISTS = "Ya existe un usuario con ese correo electrónico o ID";

    public UserManagementException(String message) {
        super(message);
    }
}

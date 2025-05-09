package moduloGestionUsuarios.UserManagement.exception;

/**
 * Exception class used to handle errors related to user management operations.
 * This custom exception class provides specific error messages for different user management issues.
 * It extends {@link Exception} and includes several static constant messages that can be used to provide
 * more descriptive error messages related to user creation, authentication, schedule issues, and more.
 */
public class UserManagementException extends Exception {

    public static String User_Not_Found = "El nombre de usuario no fue encontrado";
    public static String Incorrect_password = "La contraseña es incorrecta";
    public static String User_Not_Exist = "El usuario que cumple todos los filtros no existe";
    public static final String INVALID_SCHEDULE = "El horario asignado es incorrecto";
    public static final String USER_ALREADY_EXISTS = "Ya existe un usuario con ese correo electrónico o ID";

    public UserManagementException(String message) {
        super(message);
    }
}

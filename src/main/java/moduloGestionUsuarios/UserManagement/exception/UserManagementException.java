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
    public static String User_Not_Exist = "El usuario no existe";
    public static String Student_Exist="Ya existe un estudiante con ese id";
    public static String Admin_Exist="Ya existe un usuario con ese id";

    public UserManagementException(String message) {
        super(message);
    }
}

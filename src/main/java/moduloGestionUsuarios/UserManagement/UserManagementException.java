package moduloGestionUsuarios.UserManagement;

public class UserManagementException extends Exception {

    public static String User_Not_Found = "El nombre de usuario no fue encontrado";
    public static String Incorrect_password = "La contraseña es incorrecta";
    public static String User_Not_Exist = "El usuario que cumple todos los filtros no existe";

    public UserManagementException(String message) {
        super(message);
    }
}

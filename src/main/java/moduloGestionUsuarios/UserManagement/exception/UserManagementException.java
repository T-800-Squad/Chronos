package moduloGestionUsuarios.UserManagement.exception;

public class UserManagementException extends Exception {

    public static String User_Not_Found = "El nombre de usuario no fue encontrado";
<<<<<<< HEAD:src/main/java/moduloGestionUsuarios/UserManagement/UserManagementException.java
    public static String Incorrect_password = "La contraseña es incorrecta";
    public static String User_Not_Exist = "El usuario que cumple todos los filtros no existe";
=======
    public static String Incorrect_password = "la contraseña es incorrecta";
    public static final String INVALID_SCHEDULE = "El horario asignado es incorrecto";
    public static final String USER_ALREADY_EXISTS = "Ya existe un usuario con ese correo electrónico o ID";
>>>>>>> 6e39dcaf4a4606974e5be6703db8ef7c7137320b:src/main/java/moduloGestionUsuarios/UserManagement/exception/UserManagementException.java

    public UserManagementException(String message) {
        super(message);
    }
}

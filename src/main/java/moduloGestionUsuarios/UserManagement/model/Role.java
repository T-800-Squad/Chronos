package moduloGestionUsuarios.UserManagement.model;

/**
 * Enum representing the different roles within the system.
 * The roles determine the permissions and the type of user in the system.
 * Each role has a description associated with it, and this description is used for matching user input.
 */
public enum Role {
    STUDENT("Student"),ADMIN("Administrator"),SALA_ADMIN("Sala_Administrator")
    ,TRAINER("Trainer"),MEDICAL_SECRETARY("Medical_Secretary"),DOCTOR("Doctor")
    ,TEACHER("Teacher"),EXTRACURRICULAR_TEACHER("Extracurricular_Teacher"), BIENESTAR("Bienestar");
    private final String description;
    Role(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public static Role fromDescription(String description) {
        for (Role role : Role.values()) {
            if (role.getDescription().equalsIgnoreCase(description)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No se encontró un Role con la descripción: " + description);
    }
}

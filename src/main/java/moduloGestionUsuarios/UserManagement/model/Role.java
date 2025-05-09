package moduloGestionUsuarios.UserManagement.model;

public enum Role {
    STUDENT("Student"),ADMIN("Administrator"),SALA_ADMIN("Sala_Administratot")
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

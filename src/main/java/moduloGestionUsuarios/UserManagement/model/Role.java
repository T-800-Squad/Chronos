package moduloGestionUsuarios.UserManagement.model;

public enum Role {
    STUDENT("Student"),ADMIN("Administrator"),SALA_ADMIN("Sala_Administratot")
    ,TRAINER("Trainer"),MEDICAL_SECRETARY("Medical_Secretary"),DOCTOR("Doctor")
    ,TEACHER("Teacher"),EXTRACURRICULAR_TEACHER("Extracurricular_Teacher");
    private final String description;
    Role(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}

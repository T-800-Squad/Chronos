package moduloGestionUsuarios.UserManagement.model;

public enum Specialty {
    GENERAL_MEDICINE("General Medicine"),ODONTOLOGY("Odontology"),PSICOLOGY("Psycology");
    private final String description;
    Specialty(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}

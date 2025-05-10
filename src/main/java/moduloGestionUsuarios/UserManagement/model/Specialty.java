package moduloGestionUsuarios.UserManagement.model;

/**
 * Enum representing the different specialties available for an administrator (specifically for doctors).
 * This enum is used to specify the medical specialty of a doctor. The specialties available are:
 * General Medicine, Odontology, Psycology.
 */
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

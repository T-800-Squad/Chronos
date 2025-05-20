package moduloGestionUsuarios.UserManagement.response;

/**
 * Represents a simplified API error response structure.
 * Typically used to encapsulate error status codes and messages when handling exceptions.
 */
public class ApiResponseException{
    private int status;
    private String message;

    public ApiResponseException(int status, String message) {
        this.status = status;
        this.message = message;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}

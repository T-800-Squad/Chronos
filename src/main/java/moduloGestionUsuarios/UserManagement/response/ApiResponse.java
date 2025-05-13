package moduloGestionUsuarios.UserManagement.response;

/**
 * Generic wrapper for API responses.
 * Encapsulates the HTTP status code, a message, and any data returned by the server.
 *
 * @param <T> The type of the data contained in the response.
 */
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;

    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}

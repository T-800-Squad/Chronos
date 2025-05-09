package moduloGestionUsuarios.UserManagement.exception;

import moduloGestionUsuarios.UserManagement.response.ApiResponseException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserManagementException.class)
    public ResponseEntity<ApiResponseException> handleUserManagementException(UserManagementException ex) {
        ApiResponseException response = new ApiResponseException(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponseException> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        ApiResponseException response = new ApiResponseException(
                HttpStatus.BAD_REQUEST.value(),
                "Error de integridad en la base de datos: " + ex.getMostSpecificCause().getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}

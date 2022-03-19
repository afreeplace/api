package seyfa.afreeplace.exceptions.handler;

import seyfa.afreeplace.exceptions.FormException;
import seyfa.afreeplace.exceptions.ManagerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionManager extends ResponseEntityExceptionHandler {

    /**
     * Handle logic exceptions
     * MANAGER EXCEPTION
     */
    @ExceptionHandler(ManagerException.class)
    public ResponseEntity<Map<String, Object>> handleManagerException(HttpServletRequest request, ManagerException exception) {

        this.showError(request, exception);

        Map<String, Object> result = new HashMap<>();
        result.put("error", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(result);
    }

    /**
     * Handle auth exceptions
     * AUTH EXCEPTION
     */
    @ExceptionHandler({InternalAuthenticationServiceException.class, BadCredentialsException.class})
    public ResponseEntity<Map<String, Object>> handleAuthenticationException(Exception exception) {

        this.showError(null, exception);

        Map<String, Object> result = new HashMap<>();
        result.put("error", "Invalid authentication.");

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(result);
    }

    /**
     * Handle form error exceptions
     * FORM EXCEPTION
     */
    @ExceptionHandler(FormException.class)
    public ResponseEntity<Map<String, Object>> handleFormExceptions(HttpServletRequest request, FormException exception) {

        this.showError(request, exception);

        Map<String, Object> result = new HashMap<>();
        result.put("error", "Invalid Data.");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(result);
    }

    /**
     * Handle all others exceptions
     * ALL OTHER EXCEPTION
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleOthersException(HttpServletRequest request, Exception exception) {

        logger.fatal("Imprevisible exception error catched");
        this.showError(request, exception);

        Map<String, Object> result = new HashMap<>();
        result.put("error", "Internal Error.");

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(result);
    }

    private void showError(HttpServletRequest request, Exception exception) {
        if(request != null) {
            System.err.println("ERROR URI " + request.getRequestURI());
            logger.error("ERROR URI " + request.getRequestURI());
        }

        System.err.println("Exception: " + exception);
        exception.printStackTrace();
        logger.error("Exception: " + exception);
    }

}

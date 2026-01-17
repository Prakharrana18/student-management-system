package StudentManagement.StudentManagement.exceptionHandler;

import StudentManagement.StudentManagement.exception.UserAlreadyExistException;
import StudentManagement.StudentManagement.exception.UserNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse>handleAccessDeniedException(AccessDeniedException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse.builder().
                errorCode("ACCESS_DENIED").message("Access Denied").build());
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse>handleAuthenticationException(AuthenticationException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.builder().
                errorCode("UNAUTHORIZED_USER").message("Unauthorized").build());
    }
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse>handleUserAlreadyExist(UserAlreadyExistException ex, HttpServletRequest httpRequest){
        ErrorResponse apiException=ErrorResponse.builder()
               .success(false)
               .errorCode("USER_ALREADY_EXIST")
               .message(ex.getMessage())
               .build();
        return  new ResponseEntity<>(apiException,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
        String message=
        ex.getBindingResult().getFieldErrors()
                .stream().map(error->
                        error.getField()+ " "+ error.getDefaultMessage()).findFirst().orElse("Validation failed");
    return ResponseEntity.badRequest().body(ErrorResponse.builder().success(false).
            errorCode("VALIDATION_ERROR").message(message).localDateTime(LocalDateTime.now()).build());
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse>handleUserNotFoundException(UserNotFoundException ex){
        ErrorResponse apiException= ErrorResponse.builder()
                .success(false)
                .errorCode("STUDENT_NOT_FOUND")
                .message(ex.getMessage()).build();
        return new ResponseEntity<>(apiException,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwt(
            JwtException ex) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.builder().errorCode("INVALID_JWT").
                        message("Invalid Jwt token").
                        success(false).build());
    }

}

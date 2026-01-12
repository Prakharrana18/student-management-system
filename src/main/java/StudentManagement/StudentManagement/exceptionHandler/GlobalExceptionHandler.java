package StudentManagement.StudentManagement.exceptionHandler;

import StudentManagement.StudentManagement.exception.UserAlreadyExistException;
import StudentManagement.StudentManagement.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


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

}

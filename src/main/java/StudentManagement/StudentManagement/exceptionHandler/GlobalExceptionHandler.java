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

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ApiException>handleUserAlreadyExist(UserAlreadyExistException ex, HttpServletRequest httpRequest){
       ApiException apiException= new ApiException(HttpStatus.CONFLICT,ex.getMessage());
        return  new ResponseEntity<>(apiException,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>>handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
        Map<String,String>errors= new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error->
                        errors.put(error.getField(),error.getDefaultMessage()));
    return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiException>handleUserNotFoundException(UserNotFoundException ex){
        ApiException apiException= new ApiException(HttpStatus.NOT_FOUND,ex.getMessage());
        return new ResponseEntity<>(apiException,HttpStatus.NOT_FOUND);
    }

}

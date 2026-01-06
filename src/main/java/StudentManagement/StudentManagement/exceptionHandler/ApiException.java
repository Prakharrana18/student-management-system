package StudentManagement.StudentManagement.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiException {

    HttpStatus status;
    String message;
    LocalDateTime localDateTime;

    public ApiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.localDateTime = LocalDateTime.now();
    }
}

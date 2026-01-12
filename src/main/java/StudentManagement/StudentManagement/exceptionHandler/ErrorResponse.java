package StudentManagement.StudentManagement.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ErrorResponse {

    boolean success;
    String message;
    String errorCode;
    LocalDateTime localDateTime;

//    public ErrorResponse(boolean success,String  errorCode, String message) {
//        this.success=success;
//        this.errorCode = errorCode;
//        this.message = message;
//        this.localDateTime = LocalDateTime.now();
//    }
}

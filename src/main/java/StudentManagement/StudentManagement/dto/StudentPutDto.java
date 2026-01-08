package StudentManagement.StudentManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class StudentPutDto {
//    @NotNull(message = "name should not be null")
    String name;
//    @Email(message = "please enter a valid email")
    String email;
//    @Min(value = 5, message = "Age must be greater than 5")
    int age;
}

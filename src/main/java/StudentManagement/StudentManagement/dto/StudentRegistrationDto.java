package StudentManagement.StudentManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRegistrationDto {

    @NotBlank(message = "Name should not be blank")
    String name;
    @Email(message = "please enter a valid email")
    String email;
    @Min(value = 5,message ="age must be greater than 5" )
    int age;
}

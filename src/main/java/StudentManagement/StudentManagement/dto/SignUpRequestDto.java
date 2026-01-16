package StudentManagement.StudentManagement.dto;

import StudentManagement.StudentManagement.entity.type.RoleType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
    @NotNull
    String username;
    @NotNull
    String password;
    @NotNull
    String name;
    @Min(1)
    int age;

    Set<RoleType>roles= new HashSet<>();
}

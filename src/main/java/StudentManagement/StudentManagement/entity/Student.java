package StudentManagement.StudentManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(unique = true,nullable = false)
    private String name;

    @Email
    private String email;
//    @Min(5)
@Min(1)
@Column(nullable = false)
    private int age;

    private String course;

    @Column(nullable = false)
    private Boolean active=true;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)         // what happens with this n it we create the userid that will be same as userid of userTable
    private User user;
    @CreationTimestamp
    LocalDateTime createAt;
}

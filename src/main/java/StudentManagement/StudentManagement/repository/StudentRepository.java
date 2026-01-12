package StudentManagement.StudentManagement.repository;

import StudentManagement.StudentManagement.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

public interface StudentRepository  extends JpaRepository<Student, Long> {
        Optional<Student>findByEmail(String email);
        @Query("select s from Student s where s.active ")
        Page<Student>findAllActiveStudent(Pageable pageable);
    }

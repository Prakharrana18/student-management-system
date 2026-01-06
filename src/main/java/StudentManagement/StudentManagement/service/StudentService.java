package StudentManagement.StudentManagement.service;

import StudentManagement.StudentManagement.dto.StudentRegistrationDto;
import StudentManagement.StudentManagement.dto.StudentRegistrationResponseDto;
import StudentManagement.StudentManagement.entity.Student;
import StudentManagement.StudentManagement.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    @Autowired
    StudentRepository studentRepository;
    public StudentRegistrationResponseDto registerStudent(StudentRegistrationDto newStudent){
         Student student=studentRepository.findByEmail(newStudent.getEmail()).orElse(null);
        StudentRegistrationResponseDto responseDto= null;
         if(student==null) {
             Student studentNew = Student.builder()
                     .email(newStudent.getEmail())
                     .age(newStudent.getAge())
                     .name(newStudent.getName())
                     .build();
             studentNew = studentRepository.save(studentNew);
             return new StudentRegistrationResponseDto(studentNew.getId(),
                     studentNew.getName(), "User successfully register");
         }
         return null;
    }
}

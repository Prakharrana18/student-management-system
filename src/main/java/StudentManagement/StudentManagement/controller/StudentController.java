package StudentManagement.StudentManagement.controller;

import StudentManagement.StudentManagement.dto.StudentRegistrationDto;
import StudentManagement.StudentManagement.dto.StudentRegistrationResponseDto;
import StudentManagement.StudentManagement.entity.Student;
import StudentManagement.StudentManagement.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class StudentController {

    @Autowired
    StudentService studentService;
    @PostMapping("/register")
    public ResponseEntity<StudentRegistrationResponseDto> registerStudent(@RequestBody StudentRegistrationDto studentRegistrationDto){

        return ResponseEntity.ok(studentService.registerStudent(studentRegistrationDto));
    }
}

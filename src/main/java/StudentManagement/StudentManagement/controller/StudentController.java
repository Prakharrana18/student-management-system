package StudentManagement.StudentManagement.controller;

import StudentManagement.StudentManagement.dto.StudentPatchDto;
import StudentManagement.StudentManagement.dto.StudentPutDto;
import StudentManagement.StudentManagement.dto.StudentRegistrationDto;
import StudentManagement.StudentManagement.dto.StudentRegistrationResponseDto;
import StudentManagement.StudentManagement.entity.Student;
import StudentManagement.StudentManagement.service.StudentService;
import ch.qos.logback.classic.spi.IThrowableProxy;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class StudentController {

    @Autowired
    StudentService studentService;
    @PostMapping("/register")
    public ResponseEntity<StudentRegistrationResponseDto> registerStudent(@Valid @RequestBody StudentRegistrationDto studentRegistrationDto){

        return ResponseEntity.ok(studentService.registerStudent(studentRegistrationDto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Student>>getAllStudent(){
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/Student/{id}")
    public ResponseEntity<Student>getById(@PathVariable long id){
        return ResponseEntity.ok(studentService.findById(id));
    }
    @PutMapping("/Student/{id}")
    public String studentPut(@PathVariable long id,@Valid @RequestBody  StudentPutDto studentPutDto){
        return studentService.studentPut(id,studentPutDto);
    }
    @PatchMapping("/Student/{id}")
    public String studentPatch(@PathVariable long id, @RequestBody StudentPatchDto studentPatchDto){
        return studentService.studentPatch(id,studentPatchDto);
    }

}

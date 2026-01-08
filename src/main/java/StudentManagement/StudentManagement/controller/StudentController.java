package StudentManagement.StudentManagement.controller;

import StudentManagement.StudentManagement.dto.*;
import StudentManagement.StudentManagement.entity.Student;
import StudentManagement.StudentManagement.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RestController
@RequestMapping("api/v1")
public class StudentController {

    @Autowired
    StudentService studentService;
    @PostMapping("/register")
    public ResponseEntity<StudentRegistrationResponseDto> registerStudent(@Valid @RequestBody StudentRegistrationDto studentRegistrationDto){

        return ResponseEntity.ok(studentService.registerStudent(studentRegistrationDto));
    }

    //this is commented beacuse you cannot direclty put Page response in coontroller it is spring internal if something changes is spring then your system breaks
// and this will expose all the pagination details that we dont want to show to client or frontend so better to use Dto

//    @GetMapping("/getAll")
//    public ResponseEntity<Page<StudentResponseDto>>getAllStudent(@RequestParam(defaultValue = "0") int page,
//                                                                 @RequestParam(defaultValue = "4")int size,
//                                                                 @RequestParam(defaultValue = "id")String sortBy,
//                                                                 @RequestParam(defaultValue = "desc")String direction){
//        Sort sort=direction.equalsIgnoreCase("desc")?
//                Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
//
//        Pageable pageable=  PageRequest.of(page,size,sort);
//        return ResponseEntity.ok(studentService.getAllStudents(pageable));
//    }
//
    @GetMapping("/getAll")
    public ResponseEntity<PageResponse<StudentResponseDto>>getAllStudent(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "4")int size,
                                                                 @RequestParam(defaultValue = "id")String sortBy,
                                                                 @RequestParam(defaultValue = "desc")String direction){
        Sort sort=direction.equalsIgnoreCase("desc")?
                Sort.by(sortBy).descending():Sort.by(sortBy).ascending();

        Pageable pageable=  PageRequest.of(page,size,sort);
        return ResponseEntity.ok(studentService.getAllStudents(pageable));
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student>getById(@PathVariable long id){
        return ResponseEntity.ok(studentService.findById(id));
    }
    @PutMapping("/student/{id}")
    public String studentPut(@PathVariable long id,@Valid @RequestBody  StudentPutDto studentPutDto){
        return studentService.studentPut(id,studentPutDto);
    }
    @PatchMapping("/student/{id}")
    public String studentPatch(@PathVariable long id, @RequestBody StudentPatchDto studentPatchDto){
        return studentService.studentPatch(id,studentPatchDto);
    }

}

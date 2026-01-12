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
    public ResponseEntity<ApiResponse<StudentRegistrationResponseDto>> registerStudent(@Valid @RequestBody StudentRegistrationDto studentRegistrationDto){

        StudentRegistrationResponseDto studentResponseDto=studentService.registerStudent(studentRegistrationDto);
        return ResponseEntity.ok(ApiResponse.success(studentResponseDto));
    }

    //this is commented because you cannot direclty put Page response in coontroller it is spring internal if something changes is spring then your system breaks
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
    public ResponseEntity<ApiResponse<PageResponse<StudentResponseDto>>>getAllStudent(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "4")int size,
                                                                 @RequestParam(defaultValue = "id")String sortBy,
                                                                 @RequestParam(defaultValue = "desc")String direction){
        Sort sort=direction.equalsIgnoreCase("desc")?
                Sort.by(sortBy).descending():Sort.by(sortBy).ascending();

        Pageable pageable=  PageRequest.of(page,size,sort);
        PageResponse<StudentResponseDto> responseDtoPageResponse = studentService.getAllStudents(pageable);
        return ResponseEntity.ok(ApiResponse.success(responseDtoPageResponse));
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<ApiResponse<Student>>getById(@PathVariable long id){
        Student student=studentService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(student));
    }
    @PutMapping("/student/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDto>> studentPut(@PathVariable long id,@Valid @RequestBody  StudentPutDto studentPutDto){
        return  ResponseEntity.ok(ApiResponse.success(studentService.studentPut(id,studentPutDto)));
    }
    @PatchMapping("/student/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDto>> studentPatch(@PathVariable long id, @RequestBody StudentPatchDto studentPatchDto){
        return ResponseEntity.ok(ApiResponse.success(studentService.studentPatch(id,studentPatchDto)));
    }
    @DeleteMapping("/student/{id}")
    public ResponseEntity<ApiResponse<InactiveStudentDtoResponse>> deleteStudent(@PathVariable long id){
        return ResponseEntity.ok(ApiResponse.success(studentService.deleteStudent(id)));
    }

}

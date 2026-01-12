package StudentManagement.StudentManagement.service;

import StudentManagement.StudentManagement.dto.*;
import StudentManagement.StudentManagement.entity.Student;
import StudentManagement.StudentManagement.exception.UserAlreadyExistException;
import StudentManagement.StudentManagement.exception.UserNotFoundException;
import StudentManagement.StudentManagement.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;


@Service
@RequiredArgsConstructor
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public StudentRegistrationResponseDto registerStudent(StudentRegistrationDto newStudent) {
        Student student = studentRepository.findByEmail(newStudent.getEmail()).orElse(null);
        StudentRegistrationResponseDto responseDto = null;
        if (student == null) {
            Student studentNew = Student.builder()
                    .email(newStudent.getEmail())
                    .age(newStudent.getAge())
                    .name(newStudent.getName())
                    .active(true)
                    .build();
            studentNew = studentRepository.save(studentNew);
            return new StudentRegistrationResponseDto(studentNew.getId(),
                    studentNew.getName(), "User successfully register");
        } else {
            throw new UserAlreadyExistException();
        }
    }

    public PageResponse<StudentResponseDto> getAllStudents(Pageable pageable) {
        Page<Student> studentPage=studentRepository.findAllActiveStudent(pageable);
        List<StudentResponseDto>students=studentPage.getContent().stream()
                .map(this::mapToResponseDto).toList();
        return new PageResponse<>(
                students,
                studentPage.getNumber(),
                studentPage.getSize(),
                studentPage.getTotalElements(),
                studentPage.getTotalPages(),
                studentPage.isLast());
    }

    public Student findById(long id) {
        return studentRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
    }

    @Transactional
    public StudentResponseDto studentPut(long id, StudentPutDto studentPutDto) {
        Student student = studentRepository.findById(id).orElseThrow(UserNotFoundException::new);
        try {

                student.setAge(studentPutDto.getAge());
                student.setEmail(studentPutDto.getEmail());
                student.setName(studentPutDto.getName());
                studentRepository.save(student);

        }catch (Exception ex){
            throw  new UserNotFoundException();
        }
        return mapToResponseDto(student);
    }

    @Transactional
    public StudentResponseDto studentPatch(long id, StudentPatchDto studentPatchDto) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student != null) {
            if (studentPatchDto.getAge() != null && studentPatchDto.getAge() >= 5) {
                student.setAge(studentPatchDto.getAge());
            }
            if (studentPatchDto.getEmail() != null) {
                student.setEmail(studentPatchDto.getEmail());
            }
            if (studentPatchDto.getName() != null) {
                student.setName(studentPatchDto.getName());
            }
            studentRepository.save(student);
        } else {
            throw new UserNotFoundException();
        }
        return mapToResponseDto(student);
    }

    @Transactional
    public InactiveStudentDtoResponse deleteStudent(long id){
        Student student= studentRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if(Boolean.FALSE.equals(student.getActive())){
            return  new InactiveStudentDtoResponse("User already Deleted");
        }
       student.setActive(false);
       studentRepository.save(student);
    return  new InactiveStudentDtoResponse("Student Deleted Successfully");
    }
    private StudentResponseDto mapToResponseDto(Student student) {
        return StudentResponseDto.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .age(student.getAge())
                .status(student.getActive()?"Active":"Inactive")
                .build();
    }
}
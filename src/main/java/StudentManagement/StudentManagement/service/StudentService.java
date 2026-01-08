package StudentManagement.StudentManagement.service;

import StudentManagement.StudentManagement.dto.StudentPatchDto;
import StudentManagement.StudentManagement.dto.StudentPutDto;
import StudentManagement.StudentManagement.dto.StudentRegistrationDto;
import StudentManagement.StudentManagement.dto.StudentRegistrationResponseDto;
import StudentManagement.StudentManagement.entity.Student;
import StudentManagement.StudentManagement.exception.UserAlreadyExistException;
import StudentManagement.StudentManagement.exception.UserNotFoundException;
import StudentManagement.StudentManagement.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
         }else{
              throw  new UserAlreadyExistException();
         }
    }
    public List<Student>getAllStudents(){
        return studentRepository.findAll();
    }

    public Student findById(long id){
        return studentRepository.findById(id).orElseThrow(()->new UserNotFoundException());
    }

    public String studentPut(long id , StudentPutDto studentPutDto) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student != null) {
            student.setAge(studentPutDto.getAge());
            student.setEmail(studentPutDto.getEmail());
            student.setName(studentPutDto.getName());
            studentRepository.save(student);
        } else {
            throw new UserNotFoundException();
        }
        return "User Updated with put";
    }
        public String studentPatch(long id , StudentPatchDto studentPatchDto){
        Student student= studentRepository.findById(id).orElse(null);
        if(student!=null){
            if(studentPatchDto.getAge()!=null&&studentPatchDto.getAge()>=5) {
                student.setAge(studentPatchDto.getAge());
            }
            if(studentPatchDto.getEmail()!=null) {
                student.setEmail(studentPatchDto.getEmail());
            }
            if(studentPatchDto.getName()!=null) {
                student.setName(studentPatchDto.getName());
            }
            studentRepository.save(student);
        }else{
            throw new UserNotFoundException();
        }
    return "User Updated with patch";
}
    }

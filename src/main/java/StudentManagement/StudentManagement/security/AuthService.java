package StudentManagement.StudentManagement.security;

import StudentManagement.StudentManagement.dto.LoginRequestDto;
import StudentManagement.StudentManagement.dto.LoginResponseDto;
import StudentManagement.StudentManagement.dto.SignUpRequestDto;
import StudentManagement.StudentManagement.dto.SignupResponseDto;
import StudentManagement.StudentManagement.entity.Student;
import StudentManagement.StudentManagement.entity.User;
import StudentManagement.StudentManagement.exception.UserAlreadyExistException;
import StudentManagement.StudentManagement.repository.StudentRepository;
import StudentManagement.StudentManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService {

  private  final  AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;

    public ResponseEntity<LoginResponseDto>login(LoginRequestDto loginRequestDto){
//give the login task to authenticationManager
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword()));
log.info("reached login authService  "+authentication);
        User user= (User) authentication.getPrincipal();
        log.info("reached login authService  "+user);

        String jwtToken= authUtil.generateToken(user);
        log.info("reached login authService  "+jwtToken);
        return  ResponseEntity.ok(new LoginResponseDto(user.getId(),jwtToken));
    }
    public SignupResponseDto signup(SignUpRequestDto signupRequestDto) {
        log.info("reached signUp authService"+signupRequestDto);
        User user = signUpInternal(signupRequestDto, null);
        log.info("reached signUp authService  "+user);
        return new SignupResponseDto(user.getId(), user.getUsername());
    }
    public User signUpInternal(SignUpRequestDto signupRequestDto, String providerId) {
        User user = userRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);
        log.info("reached signUpInternal authService  "+user);
        if(user != null) throw new UserAlreadyExistException();
        log.info("reached signUpInternal authService 33");
        user = User.builder()
                .username(signupRequestDto.getUsername())
                .roles(signupRequestDto.getRoles())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))

                .build();

        user=userRepository.save(user);
        Student student= Student.builder()
                .name(signupRequestDto.getName())
                .email(user.getUsername())
                .active(true)
                .age(signupRequestDto.getAge())
                .user(user)
                .build();

        log.info("Student  "+ student);
        studentRepository.save(student);
        return user;
    }
}

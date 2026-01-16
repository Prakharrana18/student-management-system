package StudentManagement.StudentManagement.controller;

import StudentManagement.StudentManagement.dto.LoginRequestDto;
import StudentManagement.StudentManagement.dto.LoginResponseDto;
import StudentManagement.StudentManagement.dto.SignUpRequestDto;
import StudentManagement.StudentManagement.dto.SignupResponseDto;
import StudentManagement.StudentManagement.security.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto>login( @Valid @RequestBody  LoginRequestDto loginRequestDto){
        log.info("reached login controller  "+loginRequestDto);
        return authService.login(loginRequestDto);
    }
   @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto>signup(@Valid  @RequestBody SignUpRequestDto signUpRequestDto){
        return ResponseEntity.ok(authService.signup(signUpRequestDto));
    }

}

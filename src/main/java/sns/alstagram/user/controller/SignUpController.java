package sns.alstagram.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sns.alstagram.user.domain.UserRepository;
import sns.alstagram.user.dto.SignUpDto;
import sns.alstagram.user.service.UserService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class SignUpController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/sign-up")
    public String signUpPage() {
        return "sign-up";
    }

    @PostMapping(value = "/sign-up")
    @ResponseBody
    public ResponseEntity signUp(@Valid @RequestBody SignUpDto signUpDto) {

        if(userRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity("already existing email.", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByNickname(signUpDto.getNickname())) {
            return new ResponseEntity("already existing nickname", HttpStatus.BAD_REQUEST);
        }

        signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        userService.saveUser(signUpDto);

        return new ResponseEntity("signup success", HttpStatus.OK);

    }
}

package sns.alstagram.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sns.alstagram.user.dto.SignUpDto;
import sns.alstagram.user.service.UserService;

@Controller
@RequiredArgsConstructor
public class SignUpController {

    private final UserService userService;

    @GetMapping("/sign-up")
    public String signUpPage(@ModelAttribute SignUpDto signUpDto) {
        return "sign-up";
    }

    @PostMapping("/sign-up")

    public String signUp(@ModelAttribute SignUpDto signUpDto) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        signUpDto.setPassword(encoder.encode(signUpDto.getPassword()));

        userService.saveUser(signUpDto);

        return "redirect:/";
    }
}

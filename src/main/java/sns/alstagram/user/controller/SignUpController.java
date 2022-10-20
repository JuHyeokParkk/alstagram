package sns.alstagram.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sns.alstagram.mail.MailService;
import sns.alstagram.user.EmailAuthInfo;
import sns.alstagram.user.domain.UserRepository;
import sns.alstagram.user.dto.SignUpDto;
import sns.alstagram.user.repository.TempUserRepository;
import sns.alstagram.user.service.UserService;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class SignUpController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final TempUserRepository tempUserRepository;

    @GetMapping("/sign-up")
    public String signUpPage() {
        return "sign-up";
    }

    @PostMapping(value = "/sign-up")
    @ResponseBody
    public ResponseEntity signUp(@Valid @RequestBody SignUpDto signUpDto) throws MessagingException, IOException {

        if(userRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity("already existing email.", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByNickname(signUpDto.getNickname())) {
            return new ResponseEntity("already existing nickname", HttpStatus.BAD_REQUEST);
        }

        signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        long userId = tempUserRepository.addEmailAuthInfo(signUpDto);
        EmailAuthInfo emailAuthInfo = tempUserRepository.getEmailAuthInfo(userId);

        mailService.sendEmailWithAttachment(userId, emailAuthInfo.getEmail(), emailAuthInfo.getUuid());

        return new ResponseEntity("signup success", HttpStatus.OK);

    }
}

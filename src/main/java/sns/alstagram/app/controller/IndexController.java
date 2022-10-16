package sns.alstagram.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sns.alstagram.authentication.JwtTokenProvider;
import sns.alstagram.authentication.RefreshToken;
import sns.alstagram.authentication.RefreshTokenProvider;
import sns.alstagram.mail.MailService;
import sns.alstagram.user.domain.User;
import sns.alstagram.user.dto.LoginDto;
import sns.alstagram.user.service.UserService;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final RefreshTokenProvider refreshTokenProvider;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final MailService mailService;

    @GetMapping("/")
    public String indexPage() throws MessagingException, IOException {
        mailService.sendEmailWithAttachment();
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        User user = (User) authentication.getPrincipal();

        String jwt = jwtTokenProvider.generateJwt(user);
        RefreshToken refreshToken = refreshTokenProvider.generateAndSaveRefreshToken(user.getId());

        Cookie jwtInCookie = new Cookie("jwt", jwt);
        jwtInCookie.setMaxAge(60 * 60 * 24);
        jwtInCookie.setPath("/");

        Cookie refreshTokenInCookie = new Cookie("refresh", refreshToken.getRefreshToken());
        jwtInCookie.setMaxAge(60 * 60 * 60 * 60);
        jwtInCookie.setPath("/");

        response.addCookie(jwtInCookie);
        response.addCookie(refreshTokenInCookie);

        return new ResponseEntity("go feed", HttpStatus.OK);



    }
}

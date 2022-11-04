package sns.alstagram.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sns.alstagram.app.EmailAuthDto;
import sns.alstagram.authentication.JwtTokenProvider;
import sns.alstagram.authentication.RefreshToken;
import sns.alstagram.authentication.RefreshTokenProvider;
import sns.alstagram.mail.MailService;
import sns.alstagram.user.domain.User;
import sns.alstagram.user.dto.LoginDto;
import sns.alstagram.user.service.UserService;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final RefreshTokenProvider refreshTokenProvider;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @GetMapping("/")
    public String indexPage(HttpServletRequest request) {

        String currentToken = jwtTokenProvider.findTokenFromCookie(request);

        if (currentToken != null && jwtTokenProvider.isValidToken(currentToken))
            return "redirect:/feed";

        return "login";
    }

    @GetMapping("/email-auth/{id}/{uuid}")
    public String emailAuth(@PathVariable long id, @PathVariable String uuid) throws Exception {

        userService.saveUser(id, uuid);

        return "email-auth";
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response) {

        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        if(userService.loadUserByUsername(email) != null) {

            User passwordFailUser = userService.loadUserByUsername(loginDto.getEmail());

            if(passwordFailUser.isLocked()) {
                return new ResponseEntity("account lock", HttpStatus.BAD_REQUEST);
            }

            passwordFailUser.failCountPlus();

            if(passwordFailUser.getLoginFailCount() == 5) {
                passwordFailUser.lockAccount();
            }

            return new ResponseEntity("wrong password", HttpStatus.BAD_REQUEST);

        }

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

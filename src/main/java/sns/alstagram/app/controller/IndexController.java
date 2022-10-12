package sns.alstagram.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import sns.alstagram.authentication.JwtTokenProvider;
import sns.alstagram.user.domain.User;
import sns.alstagram.user.dto.LoginDto;
import sns.alstagram.user.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/")
    public String indexPage(@ModelAttribute LoginDto loginDto) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginDto loginDto, HttpServletResponse response) {

        User user = userService.findByEmail(loginDto.getEmail());

        String token = jwtTokenProvider.generateToken(user);

        Cookie jwtInCookie = new Cookie("jwt", token);
        jwtInCookie.setMaxAge(60 * 60 * 24);
        jwtInCookie.setPath("/");

        response.addCookie(jwtInCookie);

        return "redirect:/feed";



    }
}

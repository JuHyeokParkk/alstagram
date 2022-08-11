package sns.alstagram.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String hello() {
        return "login";
    }

    @GetMapping("/feed")
    public String login() {
        return "feed";
    }


}

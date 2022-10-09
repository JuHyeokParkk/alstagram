package sns.alstagram.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import sns.alstagram.user.dto.LoginDto;

@Controller
public class IndexController {

    @GetMapping("/")
    public String indexPage(@ModelAttribute LoginDto loginDto) {
        return "login";
    }
}

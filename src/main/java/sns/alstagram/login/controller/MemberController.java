package sns.alstagram.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import sns.alstagram.Entity.Member;
import sns.alstagram.login.Service.MemberService;
import sns.alstagram.login.dto.LoginAccountDto;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/")
    public String loginForm(@ModelAttribute LoginAccountDto loginAccountDto) {
        return "login";
    }

    @GetMapping("/feed")
    public String goFeed() {
        return "feed";
    }

    @GetMapping("/join")
    public String join(@ModelAttribute LoginAccountDto loginAccountDto) {
        return "joinForm";
    }

    @PostMapping("/")
    public String login(@ModelAttribute LoginAccountDto loginAccountDto, BindingResult bindingResult) {

        Member loginMember = memberService.login(loginAccountDto);

        if(loginMember == null)
            return "redirect:/";

        return "redirect:/feed";

    }
    @PostMapping("/join")
    public String save(@Valid @ModelAttribute LoginAccountDto loginAccountDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "joinForm";
        }

        Member member = loginAccountDto.toEntity();
        memberService.join(member);

        return "redirect:/";

    }

}

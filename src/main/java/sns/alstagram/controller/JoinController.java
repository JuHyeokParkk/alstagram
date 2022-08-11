package sns.alstagram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import sns.alstagram.Entity.Member;
import sns.alstagram.Repository.MemberRepository;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class JoinController {

    private final MemberRepository memberRepository;

    @GetMapping("/join")
    public String join(@ModelAttribute Member member) {
        return "joinForm";
    }

    @PostMapping("/join")
    public String save(@Valid @ModelAttribute Member member, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "joinForm";
        }

        memberRepository.save(member);

        return "redirect:/";

    }


}

package sns.alstagram.login.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sns.alstagram.Entity.Member;
import sns.alstagram.login.Repository.MemberRepository;
import sns.alstagram.login.dto.LoginAccountDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

    public Member login(LoginAccountDto loginAccountDto) {

        List<Member> members = memberRepository.findAll();

        return members.stream()
                .filter(m -> m.getUsername().equals(loginAccountDto.getUsername())
                        && m.getPassword().equals(loginAccountDto.getPassword()))
                .findFirst().orElse(null);

    }
}

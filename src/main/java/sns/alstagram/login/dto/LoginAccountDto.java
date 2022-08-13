package sns.alstagram.login.dto;

import lombok.Data;
import sns.alstagram.Entity.Member;

@Data
public class LoginAccountDto {

    public String username;
    public String password;

    public Member toEntity() {
        Member member = new Member();
        member.setUsername(this.username);
        member.setPassword(this.password);

        return member;
    }
}

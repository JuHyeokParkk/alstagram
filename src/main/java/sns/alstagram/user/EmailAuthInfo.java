package sns.alstagram.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmailAuthInfo {

    private String email;
    private String password;
    private String nickname;
    private String uuid;
}

package sns.alstagram.user.repository;

import org.springframework.stereotype.Component;
import sns.alstagram.user.EmailAuthInfo;
import sns.alstagram.user.dto.SignUpDto;

import java.util.HashMap;
import java.util.UUID;

@Component
public class TempUserRepository {

    private final HashMap<Long, EmailAuthInfo> hashMap = new HashMap<>();
    private static long id = 1;

    public long addEmailAuthInfo(SignUpDto signUpDto) {

        EmailAuthInfo emailAuthInfo = EmailAuthInfo.builder()
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .uuid(UUID.randomUUID().toString())
                .nickname(signUpDto.getNickname())
                .build();

        hashMap.put(id, emailAuthInfo);

        return id++;


    }

    public EmailAuthInfo getEmailAuthInfo(long id) {
        return hashMap.get(id);
    }
}

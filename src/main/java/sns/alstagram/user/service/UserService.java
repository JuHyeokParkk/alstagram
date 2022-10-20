package sns.alstagram.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sns.alstagram.app.EmailAuthDto;
import sns.alstagram.user.EmailAuthInfo;
import sns.alstagram.user.domain.User;
import sns.alstagram.user.domain.UserRepository;
import sns.alstagram.user.dto.SignUpDto;
import sns.alstagram.user.repository.TempUserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final TempUserRepository tempUserRepository;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }



    @Transactional
    public Long saveUser(long userId, String uuid) throws Exception {

        EmailAuthInfo emailAuthInfo = tempUserRepository.getEmailAuthInfo(userId);

        if(!uuid.equals(emailAuthInfo.getUuid())) {
            throw new Exception();
        }

        List<String> roles = Arrays.asList("ROLE_USER");


        return userRepository.save(User.builder()
                .email(emailAuthInfo.getEmail())
                .roles(roles)
                .nickname(emailAuthInfo.getNickname())
                .password(emailAuthInfo.getPassword())
                .build()).getId();
    }

    @Transactional
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}

package sns.alstagram.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sns.alstagram.user.domain.User;
import sns.alstagram.user.domain.UserRepository;
import sns.alstagram.user.dto.LoginDto;
import sns.alstagram.user.dto.SignUpDto;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }



    @Transactional
    public Long saveUser(SignUpDto signUpDto) {

        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");

        return userRepository.save(User.builder()
                .email(signUpDto.getEmail())
                .roles(roles)
                .password(signUpDto.getPassword())
                .build()).getId();
    }

    @Transactional
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}

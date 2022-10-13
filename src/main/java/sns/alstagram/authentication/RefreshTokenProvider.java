package sns.alstagram.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sns.alstagram.authentication.repository.RefreshTokenRepository;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RefreshTokenProvider {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateAndSaveRefreshToken(long userId) {

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUserId(userId);
        refreshToken.setExpireTime(new Date(new Date().getTime() + 200000));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());

        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }
}

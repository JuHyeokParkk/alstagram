package sns.alstagram.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sns.alstagram.authentication.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}

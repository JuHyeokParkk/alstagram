package sns.alstagram.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sns.alstagram.Entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}

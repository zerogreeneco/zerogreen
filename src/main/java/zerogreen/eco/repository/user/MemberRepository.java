package zerogreen.eco.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zerogreen.eco.entity.userentity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

}

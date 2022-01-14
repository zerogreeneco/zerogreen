package zerogreen.eco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zerogreen.eco.entity.userentity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}

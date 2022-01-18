package zerogreen.eco.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import zerogreen.eco.entity.userentity.StoreMember;

public interface StoreMemberRepository extends JpaRepository<StoreMember, Long> {
}

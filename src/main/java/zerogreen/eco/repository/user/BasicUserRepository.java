package zerogreen.eco.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import zerogreen.eco.entity.userentity.BasicUser;

public interface BasicUserRepository extends JpaRepository<BasicUser, Long> {
    public BasicUser findByUsername(String username);
}

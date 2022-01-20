package zerogreen.eco.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import zerogreen.eco.entity.userentity.BasicUser;

import java.util.Optional;

public interface BasicUserRepository extends JpaRepository<BasicUser, Long>, BasicUserRepositoryCustom {

    Optional<BasicUser> findByUsername(String username);


}

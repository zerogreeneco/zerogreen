package zerogreen.eco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zerogreen.eco.entity.userentity.BasicUser;

public interface UserRepository extends JpaRepository<BasicUser, Long> {

}

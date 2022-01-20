package zerogreen.eco.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zerogreen.eco.entity.userentity.BasicUser;

import java.util.Optional;

public interface BasicUserRepository extends JpaRepository<BasicUser, Long>, BasicUserRepositoryCustom {

    Optional<BasicUser> findByUsername(String username);

    @Query("SELECT COUNT(bu) FROM BasicUser bu WHERE bu.phoneNumber=:phoneNumber ")
    int phoneDuplicateCheck(String phoneNumber);

    @Query("SELECT COUNT(bu) FROM BasicUser bu WHERE bu.username=:username ")
    int emailDuplicateCheck(String username);


}

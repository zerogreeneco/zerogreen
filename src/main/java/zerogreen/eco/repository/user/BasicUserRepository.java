package zerogreen.eco.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;
import zerogreen.eco.dto.store.NonApprovalStoreDto;
import zerogreen.eco.entity.userentity.BasicUser;

import java.util.Optional;

public interface BasicUserRepository extends JpaRepository<BasicUser, Long>, BasicUserRepositoryCustom {

    Optional<BasicUser> findByUsername(String username);

    long countByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

    long countByUsername(@RequestParam("username") String username);


    Optional<BasicUser> findByUsernameAndPhoneNumber(@RequestParam("username") String username, @RequestParam("phoneNumber") String phoneNumber);
    Optional<BasicUser> findByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

}

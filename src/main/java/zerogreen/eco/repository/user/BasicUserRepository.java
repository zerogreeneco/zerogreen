package zerogreen.eco.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.entity.userentity.UserRole;

import java.util.List;
import java.util.Optional;

public interface BasicUserRepository extends JpaRepository<BasicUser, Long>, BasicUserRepositoryCustom {

    Optional<BasicUser> findByUsername(String username);

    long countByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

    long countByUsername(@RequestParam("username") String username);


    Optional<BasicUser> findByUsernameAndPhoneNumber(@RequestParam("username") String username, @RequestParam("phoneNumber") String phoneNumber);
    Optional<BasicUser> findByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

    //승인받은 스토어
    @Query("select bu from BasicUser bu left outer join StoreMember sm on bu.id = sm.id " +
            " where bu.userRole ='STORE'")
    List<StoreMember> findByApprovedStore();

}

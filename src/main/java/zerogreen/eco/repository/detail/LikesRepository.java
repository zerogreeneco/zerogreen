package zerogreen.eco.repository.detail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import zerogreen.eco.dto.detail.LikesDto;
import zerogreen.eco.entity.detail.Likes;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.StoreMember;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Query("select count(l.id) from Likes l where l.storeMember =:storeMember")
    Long counting(StoreMember storeMember);

    @Query("select l from Likes l where l.storeMember =:storeMember and l.basicUser =:basicUser")
    Likes getLikesByStoreAndUser(StoreMember storeMember, BasicUser basicUser);

    @Query("select count(l.id) from Likes l where l.storeMember.id =:sno " +
            "and l.basicUser.id =:mno")
    Long cntMemberLike(@RequestParam("sno") Long sno, @RequestParam("mno") Long mno);

    @Transactional
    @Modifying
    @Query("delete from Likes l where l.storeMember.id =:sno and l.basicUser.id =:mno ")
    void deleteMemberLikes(@RequestParam("sno") Long sno, @RequestParam("mno") Long mno);

}

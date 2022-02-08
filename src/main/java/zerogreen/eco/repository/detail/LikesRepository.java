package zerogreen.eco.repository.detail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
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
    Long cntMemberLike(@Param("sno") Long sno, @Param("mno") Long mno);

    @Transactional
    @Modifying
    @Query("delete from Likes l where l.storeMember.id =:sno and l.basicUser.id =:mno ")
    void deleteMemberLikes(@Param("sno") Long sno, @Param("mno") Long mno);

    //memberMyInfo에 나타나는 회원별 좋아요 수
    @Query("select count(l.id) from Likes l where l.basicUser =:basicUser ")
    Long countLikesByUser(@Param("basicUser") BasicUser basicUser);

}

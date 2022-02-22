package zerogreen.eco.repository.detail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.entity.detail.Likes;
import zerogreen.eco.entity.userentity.BasicUser;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    //전체 좋아요 수 (Detail)
    @Query("select count(l.id) from Likes l where l.storeMember.id =:storeMember")
    Long counting(@Param("storeMember") Long storeMember);

    // 회원별 좋아요 유무 (Detail)
    @Query("select count(l.id) from Likes l where l.storeMember.id =:sno " +
            "and l.basicUser.id =:mno")
    Long cntMemberLike(@Param("sno") Long sno, @Param("mno") Long mno);

    // 좋아요 취소 (Detail)
    @Transactional
    @Modifying
    @Query("delete from Likes l where l.storeMember.id =:sno and l.basicUser.id =:mno ")
    void deleteMemberLikes(@Param("sno") Long sno, @Param("mno") Long mno);

    //회원별 전체 좋아요 수 (memberMyInfo)
    @Query("select count(l.id) from Likes l where l.basicUser =:basicUser ")
    Long countLikesByUser(@Param("basicUser") BasicUser basicUser);

    //회원별 찜한 가게 리스트 (memberMyInfo)
    @Query("select l from Likes l where l.basicUser =:basicUser")
    List<Likes> getLikesByUser(@Param("basicUser") BasicUser basicUser);

}

package zerogreen.eco.repository.community;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;
import zerogreen.eco.dto.community.CommunityReplyDto;
import zerogreen.eco.entity.community.CommunityLike;

import java.util.List;

public interface CommunityLikeRepository extends JpaRepository<CommunityLike, Long> {

    @Modifying
    @Query("DELETE FROM CommunityLike c WHERE c.board.id=:boardId AND c.basicUser.id=:memberId ")
    void deleteLike(@Param("boardId") Long boardId, @Param("memberId") Long memberId);

    @Query("SELECT COUNT(c.id) FROM CommunityLike c WHERE c.board.id=:boardId AND c.basicUser.id=:memberId ")
    int countLike(@Param("boardId") Long boardId, @Param("memberId") Long memberId);

    @Query("SELECT COUNT(c.id) FROM CommunityLike c WHERE c.board.id=:boardId ")
    int countByBoard(@Param("boardId") Long boardId);

}


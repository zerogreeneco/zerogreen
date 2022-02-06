package zerogreen.eco.repository.community;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;
import zerogreen.eco.entity.community.BoardNestedReply;

import java.util.List;

public interface CommunityNestedReplyRepository extends JpaRepository<BoardNestedReply, Long> {

    @Query("SELECT nr FROM BoardNestedReply  nr " +
            " JOIN FETCH nr.basicUser user " +
            " JOIN FETCH nr.boardReply reply " +
            " WHERE nr.boardReply.id=:replyId ")
    List<BoardNestedReply> findNestedReplyByReplyId(@Param("replyId") Long replyId);

}

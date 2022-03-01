package zerogreen.eco.repository.community;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;
import zerogreen.eco.entity.community.BoardReply;

import java.util.List;

public interface BoardReplyRepository extends JpaRepository<BoardReply, Long> {

    // 게시글별 댓글 리스트 (depth = 1 : 부모 댓글)
//    @Query("SELECT br FROM BoardReply br " +
//            " JOIN FETCH br.board board" +
//            " JOIN FETCH br.replier r " +
//            " WHERE br.depth = 1 AND br.board.id=:boardId ")
    @Query("SELECT reply FROM BoardReply reply " +
            " JOIN FETCH reply.board board " +
            " JOIN FETCH reply.replier replier " +
            " WHERE reply.board.id=:boardId " +
            " ORDER BY COALESCE(reply.parentReply.id, reply.board.id), reply.depth, reply.createdDate asc ")
    List<BoardReply> findBoardRepliesByBoardId(@Param("boardId") Long boardId);
}

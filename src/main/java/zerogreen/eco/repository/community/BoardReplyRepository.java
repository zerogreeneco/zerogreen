package zerogreen.eco.repository.community;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;
import zerogreen.eco.entity.community.BoardReply;

import java.util.List;

public interface BoardReplyRepository extends JpaRepository<BoardReply, Long> {

    @Query("SELECT br FROM BoardReply br " +
            " JOIN FETCH br.board b " +
            " JOIN FETCH br.replier r " +
            " WHERE br.board.id=:boardId ")
    List<BoardReply> findBoardRepliesByBoardId(@RequestParam("boardId") Long boardId);
}

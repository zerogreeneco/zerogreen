package zerogreen.eco.repository.community;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zerogreen.eco.entity.community.BoardImage;

import java.util.List;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {

    @Query("SELECT image " +
            "FROM BoardImage image WHERE image.board.id=:boardId ")
    List<BoardImage> findByBoard(@Param("boardId") Long boardId);
}

package zerogreen.eco.repository.community;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zerogreen.eco.entity.community.CommunityBoard;

public interface CommunityBoardRepository extends JpaRepository<CommunityBoard, Long>, CommunityBoardRepositoryCustom {

/*    @Query("SELECT board.id, board.title, board.text, board.category, m.nickname " +
            " FROM CommunityBoard board JOIN FETCH Member m ")
    Slice<CommunityBoard> findAllCommunityBoard(Pageable pageable);*/

}

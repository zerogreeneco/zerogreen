package zerogreen.eco.repository.community;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import zerogreen.eco.entity.community.CommunityBoard;

public interface CommunityBoardRepository extends JpaRepository<CommunityBoard, Long>, CommunityBoardRepositoryCustom {

    @Modifying
    @Transactional
    @Query("UPDATE CommunityBoard cb SET cb.count = cb.count + 1 WHERE cb.id=:boardId ")
    int boardCount(@RequestParam("boardId") Long boardId);
}

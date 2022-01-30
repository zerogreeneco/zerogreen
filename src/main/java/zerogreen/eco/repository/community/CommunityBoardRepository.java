package zerogreen.eco.repository.community;

import org.springframework.data.jpa.repository.JpaRepository;
import zerogreen.eco.entity.community.CommunityBoard;

public interface CommunityBoardRepository extends JpaRepository<CommunityBoard, Long>, CommunityBoardRepositoryCustom {
}

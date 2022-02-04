package zerogreen.eco.service.community;

import zerogreen.eco.dto.community.CommunityReplyDto;
import zerogreen.eco.entity.userentity.BasicUser;

import java.util.List;

public interface CommunityReplyService {

    void replySave(String text, Long boardId, BasicUser basicUser);

    List<CommunityReplyDto> findReplyByBoardId(Long boardId);
}

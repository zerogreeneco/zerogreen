package zerogreen.eco.service.community;

import zerogreen.eco.dto.community.CommunityReplyDto;
import zerogreen.eco.entity.userentity.BasicUser;

import java.util.List;

public interface CommunityNestedReplyService {

    void nestedReplySave(Long replyId, BasicUser basicUser, String text);

    List<CommunityReplyDto> findNestedReplyByReplyId(Long replyId);

}

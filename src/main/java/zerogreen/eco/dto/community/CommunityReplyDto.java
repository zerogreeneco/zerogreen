package zerogreen.eco.dto.community;

import lombok.Getter;
import lombok.Setter;
import zerogreen.eco.entity.community.BoardNestedReply;
import zerogreen.eco.entity.community.BoardReply;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.StoreMember;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CommunityReplyDto {

    private Long id;
    private String text;
    private String replier;
    private LocalDateTime createdTime;

    public CommunityReplyDto() {}

    // 댓글
    public CommunityReplyDto(BoardReply boardReply) {
        this.id = boardReply.getId();
        this.text = boardReply.getReplyContent();
        this.replier = boardReply.getReplier().getUsername();
        this.createdTime = boardReply.getCreatedDate();
    }

    // 대댓글
    public CommunityReplyDto(BoardNestedReply nestedReply) {
        this.id = nestedReply.getId();
        this.text = nestedReply.getReplyContent();
        this.replier = nestedReply.getBasicUser().getUsername();
        this.createdTime = nestedReply.getCreatedDate();
    }
}

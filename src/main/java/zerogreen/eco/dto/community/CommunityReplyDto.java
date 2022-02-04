package zerogreen.eco.dto.community;

import lombok.Getter;
import lombok.Setter;
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

    public CommunityReplyDto(BoardReply boardReply) {
//        Member member = (Member) boardReply.getReplier();
//        StoreMember store = (StoreMember) boardReply.getReplier();

        boolean b = boardReply.getReplier() instanceof StoreMember;

        this.id = boardReply.getId();
        this.text = boardReply.getReplyContent();

        this.replier = boardReply.getReplier().getUsername();
        this.createdTime = boardReply.getCreatedDate();
    }
}

package zerogreen.eco.dto.community;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerogreen.eco.entity.community.BoardNestedReply;
import zerogreen.eco.entity.community.BoardReply;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommunityReplyDto {

    private Long replyId;
    private Long boardId;
    private Long parentReplyId;
    private String text;
    private String replier;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdTime;
    private List<BoardReply> nestedReplyList = new ArrayList<>();

    public CommunityReplyDto() {}

    @Builder
    public CommunityReplyDto(Long replyId, Long boardId, Long parentReplyId, String text, String replier, LocalDateTime createdTime, List<BoardReply> nestedReplyList) {
        this.replyId = replyId;
        this.boardId = boardId;
        this.parentReplyId = parentReplyId;
        this.text = text;
        this.replier = replier;
        this.createdTime = createdTime;
        this.nestedReplyList = nestedReplyList;
    }

    // 댓글
    public CommunityReplyDto(BoardReply boardReply) {
        this.replyId = boardReply.getId();
        this.boardId = boardReply.getBoard().getId();
        this.text = boardReply.getReplyContent();
        this.replier = boardReply.getReplier().getUsername();
        this.createdTime = boardReply.getCreatedDate();
        this.nestedReplyList = boardReply.getNestedReplyList();
    }

    // 대댓글
    public CommunityReplyDto(BoardNestedReply nestedReply) {
        this.replyId = nestedReply.getId();
        this.text = nestedReply.getReplyContent();
        this.replier = nestedReply.getBasicUser().getUsername();
        this.createdTime = nestedReply.getCreatedDate();
    }
}

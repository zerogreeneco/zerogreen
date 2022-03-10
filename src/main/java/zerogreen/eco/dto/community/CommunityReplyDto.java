package zerogreen.eco.dto.community;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import zerogreen.eco.entity.community.BoardReply;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.entity.userentity.VegetarianGrade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Slf4j
public class CommunityReplyDto {

    private Long replyId;
    private Long boardId;
    private Long parentReplyId;
    private int depth;
    private String text;
    private String nickname;
    private String username;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdTime;
    private VegetarianGrade vegetarianGrade;

    public CommunityReplyDto() {
    }

    // 댓글
    public CommunityReplyDto(BoardReply boardReply) {

        // 멤버 타입에 따라서 nickname 분기
        if (boardReply.getReplier() instanceof Member) {
            this.nickname = ((Member) boardReply.getReplier()).getNickname();
            this.vegetarianGrade = ((Member) boardReply.getReplier()).getVegetarianGrade();
        } else if (boardReply.getReplier() instanceof StoreMember) {
            this.nickname = ((StoreMember) boardReply.getReplier()).getStoreName();
        } else if (boardReply.getReplier().getUserRole().equals(UserRole.ADMIN)) {
            this.nickname = boardReply.getReplier().getUsername();
        }

        this.replyId = boardReply.getId();
        this.boardId = boardReply.getBoard().getId();
        this.text = boardReply.getReplyContent();
        this.username = boardReply.getReplier().getUsername();
        this.createdTime = boardReply.getModifiedDate();
        this.depth = boardReply.getDepth();
    }
}

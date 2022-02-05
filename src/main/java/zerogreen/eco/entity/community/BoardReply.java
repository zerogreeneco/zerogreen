package zerogreen.eco.entity.community;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerogreen.eco.entity.baseentity.BaseTimeEntity;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardReply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    private String replyContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "replier_id")
    private BasicUser replier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private CommunityBoard board;

    @OneToMany(mappedBy = "boardReply", cascade = CascadeType.REMOVE)
    private List<BoardNestedReply> nestedReplies = new ArrayList<>();

    public BoardReply(String replyContent, BasicUser replier, CommunityBoard board) {
        this.replyContent = replyContent;
        this.replier = replier;
        this.board = board;
    }

    public void changeText(String replyContent) {
        this.replyContent = replyContent;
    }
}

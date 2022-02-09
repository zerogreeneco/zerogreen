package zerogreen.eco.entity.community;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerogreen.eco.entity.baseentity.BaseTimeEntity;
import zerogreen.eco.entity.userentity.BasicUser;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_reply_id", nullable = true)
    private BoardReply parentReply;

    @OneToMany(cascade = CascadeType.ALL)
    private List<BoardReply> nestedReplyList = new ArrayList<>();

    private int groupOrder = 1;
    private int depth = 1;

    public BoardReply(String replyContent, BasicUser replier, CommunityBoard board) {

        this.replyContent = replyContent;
        this.replier = replier;
        this.board = board;
        this.parentReply = parentReply;
    }

    @Builder(builderMethodName = "replyBuilder")
    public BoardReply(String replyContent, BasicUser replier, CommunityBoard board, List<BoardReply> nestedReplyList) {
        this.replyContent = replyContent;
        this.replier = replier;
        this.board = board;
        this.nestedReplyList = nestedReplyList;
    }

    public void changeText(String replyContent) {
        this.replyContent = replyContent;
    }

    public void addNestedReply(BoardReply nestedReply) {

        nestedReply.parentReply = this;
        nestedReply.depth = this.depth + 1;
        this.nestedReplyList.add(nestedReply);

    }

    private boolean hasChildren() {
        return getNestedReplyList().size() != 0;
    }

}

package zerogreen.eco.entity.community;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerogreen.eco.entity.baseentity.BaseTimeEntity;
import zerogreen.eco.entity.userentity.Member;

import javax.persistence.*;

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
    private Member replier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private CommunityBoard board;
}

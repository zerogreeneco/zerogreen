package zerogreen.eco.entity.community;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerogreen.eco.entity.baseentity.BaseTimeEntity;
import zerogreen.eco.entity.userentity.BasicUser;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardNestedReply extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "nest_reply_id")
    private Long id;

    private String replyContent;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reply_id")
    private BoardReply boardReply;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private BasicUser basicUser;
}

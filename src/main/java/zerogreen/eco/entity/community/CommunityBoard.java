package zerogreen.eco.entity.community;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerogreen.eco.entity.baseentity.BaseTimeEntity;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Lob
    private String text;

    private int count;

    private boolean chatCheck = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "board", cascade = REMOVE)
    private List<CommunityLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = REMOVE)
    private List<BoardReply> boardReplies = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<BoardImage> imageList = new ArrayList<>();

    @Builder
    public CommunityBoard(String text, Member member, Category category, boolean chatCheck) {
        this.text = text;
        this.member = member;
        this.category = category;
        this.chatCheck = chatCheck;
    }

    // 수정용 Setter
    public void changeBoard(Category category, String text, boolean chatCheck) {
        this.category = category;
        this.text = text;
        this.chatCheck = chatCheck;
    }
}

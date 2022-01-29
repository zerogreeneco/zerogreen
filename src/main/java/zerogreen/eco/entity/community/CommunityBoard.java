package zerogreen.eco.entity.community;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerogreen.eco.entity.baseentity.BaseTimeEntity;
import zerogreen.eco.entity.userentity.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;
    @Lob
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<BoardImage> imageList = new ArrayList<>();

    @Builder
    public CommunityBoard(String title, String text, Member member, Category category, List<BoardImage> imageList) {
        this.title = title;
        this.text = text;
        this.member = member;
        this.category = category;
        this.imageList = imageList;
    }
}

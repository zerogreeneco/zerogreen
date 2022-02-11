package zerogreen.eco.entity.detail;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerogreen.eco.entity.baseentity.BaseTimeEntity;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.StoreMember;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailReview extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Lob
    private String reviewText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer")
    private BasicUser reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store")
    private StoreMember storeMember;

    // 셀프 참조로 해당 댓글의 부모 댓글 PK 입력
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_review_id", nullable = true)
    private DetailReview parentReview;

    // 양방향 매핑으로 부모 댓글의 자식 댓글 리스트
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parentReview")
    private List<DetailReview> nestedReviewList = new ArrayList<>();

    @OneToMany(mappedBy = "detailReview", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<ReviewImage> imageList = new ArrayList<>();

    //계층구분
    private int depth = 1;


    //리뷰 수정
    public void editReview(String reviewText) {
        this.reviewText = reviewText;
    }

    public DetailReview(String reviewText, BasicUser reviewer, StoreMember storeMember) {
        this.reviewText = reviewText;
        this.reviewer = reviewer;
        this.storeMember = storeMember;
    }

    @Builder(builderMethodName = "reviewBuilder")
    public DetailReview(String reviewText, BasicUser reviewer, StoreMember storeMember, List<DetailReview> nestedReviewList) {
        this.reviewText = reviewText;
        this.reviewer = reviewer;
        this.storeMember = storeMember;
        this.nestedReviewList = nestedReviewList;
    }

    // 자식 댓글일 경우 부모 댓글에 리스트 형태로 추가하는 메서드
    public void addNestedReview(DetailReview nestedReview) {
        nestedReview.parentReview = this;
        // 자식 댓글이 저장되면 기본 depth 1을 2로 변경
        nestedReview.depth = this.depth + 1;
        this.nestedReviewList.add(nestedReview);
    }


}

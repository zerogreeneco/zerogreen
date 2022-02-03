package zerogreen.eco.entity.detail;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.StoreMember;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="storeReview_id")
    private Long id;

    private String storeReviewText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeMember")
    private StoreMember storeMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberReview_id")
    private MemberReview memberReview;

    //리뷰 수정하기
    public void editStoreReview(String storeReviewText){
        this.storeReviewText = storeReviewText;
    }

    //db넣기
    public StoreReview(String storeReviewText, StoreMember storeMember, MemberReview memberReview) {
        this.storeReviewText = storeReviewText;
        this.storeMember = storeMember;
        this.memberReview = memberReview;
    }

}

package zerogreen.eco.entity.detail;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerogreen.eco.entity.baseentity.BaseTimeEntity;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.StoreMember;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberReview extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_id")
    private Long id;

    @Lob
    private String reviewText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeMember")
    private StoreMember storeMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private BasicUser basicUser;


    //리뷰 수정하기
    public void editMemberReview(String reviewText){
        this.reviewText = reviewText;
    }

    //add review. Entity db랑 연결할 때
    public MemberReview(String reviewText, BasicUser basicUser, StoreMember storeMember) {
        this.reviewText = reviewText;
        this.basicUser = basicUser;
        this.storeMember = storeMember;
    }

    public MemberReview(Long id, String reviewText, BasicUser basicUser, StoreMember storeMember) {
        this.id = id;
        this.reviewText = reviewText;
        this.basicUser = basicUser;
        this.storeMember = storeMember;
    }



}

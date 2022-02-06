package zerogreen.eco.dto.detail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import zerogreen.eco.entity.detail.MemberReview;
import zerogreen.eco.entity.detail.StoreReview;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.StoreMember;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
public class MemberReviewDto {
    private Long rno;
    @NotBlank
    private String reviewText;

    private Long id;
    private String username;

    private String nickname;
    private Member member;

    private StoreMember storeMember;
    private BasicUser basicUser;

    private Long srno;
    @NotBlank
    private String storeReviewText;
    private StoreReview storeReview;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;


    //이하 생성자 정리예정
    public MemberReviewDto() {
    }

    //멤버리뷰 리스팅
    public MemberReviewDto(Long rno, String reviewText, BasicUser basicUser, StoreMember storeMember, String nickname) {
        this.rno = rno;
        this.reviewText = reviewText;
        this.basicUser = basicUser;
        this.storeMember = storeMember;
        this.nickname = nickname;
    }

    //멤버리뷰 리스팅+스토어 리뷰 (리스팅작업중)
    public MemberReviewDto(Long rno, String reviewText, BasicUser basicUser,
                           StoreMember storeMember, String nickname, StoreReview storeReview) {
        this.rno = rno;
        this.reviewText = reviewText;
        this.basicUser = basicUser;
        this.storeMember = storeMember;
        this.nickname = nickname;
        this.storeReview = storeReview;
    }


}

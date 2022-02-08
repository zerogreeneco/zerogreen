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

    //멤버리뷰 리스팅 (memberMyInfo)
    public MemberReviewDto(MemberReview memberReview) {
        this.rno = memberReview.getId();
        this.reviewText = memberReview.getReviewText();
        this.basicUser = memberReview.getBasicUser();
        this.storeMember = memberReview.getStoreMember();
        this.createdDate = memberReview.getCreatedDate();
    }

    public MemberReviewDto(Long rno,String reviewText, BasicUser basicUser, StoreMember storeMember,
                           LocalDateTime createdDate) {
        this.rno = rno;
        this.reviewText = reviewText;
        this.basicUser = basicUser;
        this.storeMember = storeMember;
        this.createdDate = createdDate;
    }

    //멤버리뷰 리스팅+스토어 리뷰 (페이징작업중)
    public MemberReviewDto(Long rno, String reviewText, BasicUser basicUser,
                           StoreMember storeMember, String nickname, StoreReview storeReview,
                           LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.rno = rno;
        this.reviewText = reviewText;
        this.basicUser = basicUser;
        this.storeMember = storeMember;
        this.nickname = nickname;
        this.storeReview = storeReview;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;

    }


}

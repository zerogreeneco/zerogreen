package zerogreen.eco.dto.detail;

import lombok.Getter;
import lombok.Setter;
import zerogreen.eco.entity.detail.MemberReview;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.StoreMember;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberReviewDto {
    private Long rno;
    private String reviewText;

    private Long id;
    private String username;

    private String storeName;
    private String nickName;

    private StoreMember storeMember;
    private BasicUser basicUser;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    //뭐하는ㄱㅓ지
    public MemberReviewDto(){

    }

    //바보갓은나애모습
    public MemberReview reviewDto(MemberReviewDto memberReviewDto) {
        return new MemberReview(memberReviewDto.getReviewText(),memberReviewDto.getBasicUser());
    }

/*
    public MemberReview memberReviewDto(MemberReviewDto memberReviewDto) {
        return new MemberReview(memberReviewDto.getUsername(), memberReviewDto.getReviewText(), memberReviewDto.getStoreName());
    }
*/

}

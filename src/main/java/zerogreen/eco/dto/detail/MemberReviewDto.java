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

    private String username;
    private String storeName;

    private Long id;
    private String nickName;

    private StoreMember storeMember;
    private BasicUser basicUser;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    //바보갓은나애모습
    public MemberReview memberReviewDto(MemberReviewDto memberReviewDto) {
        return new MemberReview(memberReviewDto.getReviewText(),memberReviewDto.getStoreMember(),memberReviewDto.getBasicUser());
    }
/*
    public MemberReview memberReviewDto(MemberReviewDto memberReviewDto) {
        return new MemberReview(memberReviewDto.getUsername(), memberReviewDto.getReviewText(), memberReviewDto.getStoreName());
    }
*/

}

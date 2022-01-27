package zerogreen.eco.dto.detail;

import lombok.Getter;
import lombok.Setter;
import zerogreen.eco.entity.detail.MemberReview;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberReviewDto {
    private Long rno;
    private String reviewText;

    private Long id;

    private String storeName;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    //바보갓은나애모습
    public MemberReview memberReviewDto(MemberReviewDto memberReviewDto) {
        return new MemberReview(memberReviewDto.getId(), memberReviewDto.getRno(),
                memberReviewDto.getReviewText(), memberReviewDto.getStoreName());
    }

}

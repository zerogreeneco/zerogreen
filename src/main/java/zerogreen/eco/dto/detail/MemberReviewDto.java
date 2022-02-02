package zerogreen.eco.dto.detail;

import lombok.Getter;
import lombok.Setter;
import zerogreen.eco.entity.detail.MemberReview;
import zerogreen.eco.entity.userentity.BasicUser;
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

    private Long sno;
    private String storeName;

    private StoreMember storeMember;
    private BasicUser basicUser;

    //이하 생성자 정리예정

    //리뷰 db넣기
/*
    public MemberReviewDto(String reviewText, String username, Long sno) {
        this.reviewText = reviewText;
        this.username = username;
        this.sno = sno;
    }
*/

    //리뷰 리스팅
    public MemberReviewDto(Long rno, String reviewText, String username, Long sno) {
        this.rno = rno;
        this.reviewText = reviewText;
        this.username = username;
        this.sno = sno;
    }

}

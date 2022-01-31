package zerogreen.eco.dto.detail;

import lombok.Getter;
import lombok.Setter;
import zerogreen.eco.entity.detail.MemberReview;
import zerogreen.eco.entity.userentity.StoreMember;

import java.time.LocalDateTime;

@Getter
@Setter
public class StoreReviewDto {
    private Long srno;
    private String storeReviewText;

    private Long rno;
    private String reviewText;

    private Long id;
    private String username;

    private Long sno;
    private String storeName;

    private StoreMember storeMember;
    private MemberReview memberReview;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;


    public StoreReviewDto(String storeReviewText, Long sno, Long rno) {
        this.storeReviewText = storeReviewText;
        this.sno = sno;
        this.rno = rno;
    }

}

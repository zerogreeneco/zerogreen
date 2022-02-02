package zerogreen.eco.dto.detail;

import lombok.Getter;
import lombok.Setter;
import zerogreen.eco.entity.detail.MemberReview;
import zerogreen.eco.entity.userentity.StoreMember;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
public class StoreReviewDto {
    private Long srno;
    @NotBlank
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


    public StoreReviewDto(Long srno, String storeReviewText, Long sno, Long rno) {
        this.srno = srno;
        this.storeReviewText = storeReviewText;
        this.sno = sno;
        this.rno = rno;
    }

}

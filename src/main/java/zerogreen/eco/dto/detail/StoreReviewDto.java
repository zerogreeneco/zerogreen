package zerogreen.eco.dto.detail;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StoreReviewDto {
    private Long srno;
    private String storeReviewText;

    private Long rno;
    private String reviewText;

    private Long id;

    private String storeName;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}

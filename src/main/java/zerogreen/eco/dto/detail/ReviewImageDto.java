package zerogreen.eco.dto.detail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import zerogreen.eco.entity.detail.DetailReview;
import zerogreen.eco.entity.detail.ReviewImage;
import zerogreen.eco.entity.userentity.StoreMember;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReviewImageDto {

    private Long id;
    private String reviewFileName;
    private String uploadFileName;
    private String filePath;
    private DetailReview detailReview;
    private StoreMember storeMember;
    private List<MultipartFile> reviewImages;
    private String thumbnailImage;


    //스토어 전체 리뷰 이미지 리스트 뿌리기
    public ReviewImageDto(ReviewImage reviewImage) {
        this.id = reviewImage.getId();
        this.reviewFileName = reviewImage.getReviewFileName();
        this.uploadFileName = reviewImage.getUploadFileName();
        this.detailReview = reviewImage.getDetailReview();
        this.storeMember = reviewImage.getStoreMember();
        this.filePath = reviewImage.getFilePath();
        this.thumbnailImage = "thumb_" + reviewImage.getReviewFileName();
    }

}

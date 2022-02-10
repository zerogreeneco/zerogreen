package zerogreen.eco.dto.detail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import zerogreen.eco.entity.detail.MemberReview;
import zerogreen.eco.entity.detail.ReviewImage;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReviewImageDto {

    private Long id;
    private String reviewFileName;
    private String uploadFileName;
    private MemberReview memberReview;
    //얘는 어디에 있어야 하는걸까..
    private List<MultipartFile> reviewImages;

    public ReviewImageDto(ReviewImage reviewImage) {
        this.id = reviewImage.getId();
        this.reviewFileName = reviewImage.getReviewFileName();
        this.uploadFileName = reviewImage.getUploadFileName();
        this.memberReview = reviewImage.getMemberReview();
    }

    public ReviewImageDto(Long id, String uploadFileName, String reviewFileName, MemberReview memberReview) {
        this.id = id;
        this.reviewFileName = reviewFileName;
        this.uploadFileName = uploadFileName;
        this.memberReview = memberReview;


    }

}

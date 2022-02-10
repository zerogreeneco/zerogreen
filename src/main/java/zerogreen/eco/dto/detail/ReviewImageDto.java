package zerogreen.eco.dto.detail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import zerogreen.eco.entity.detail.MemberReview;
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
    private MemberReview memberReview;
    private StoreMember storeMember;
    //얘는 어디에 있어야 하는걸까..
    private List<MultipartFile> reviewImages;


    //리스트 뿌리기
/*
    public ReviewImageDto(ReviewImage reviewImage) {
        this.id = reviewImage.getId();
        this.reviewFileName = reviewImage.getReviewFileName();
        this.uploadFileName = reviewImage.getUploadFileName();
        this.memberReview = reviewImage.getMemberReview();
    }
*/
    //스토어 전체 리뷰 이미지 리스트 뿌리기
    public ReviewImageDto(ReviewImage reviewImage) {
        this.id = reviewImage.getId();
        this.reviewFileName = reviewImage.getReviewFileName();
        this.uploadFileName = reviewImage.getUploadFileName();
        this.memberReview = reviewImage.getMemberReview();
        this.storeMember = reviewImage.getStoreMember();
    }

    //누구세요?
/*
    public ReviewImageDto(Long id, String uploadFileName, String reviewFileName, MemberReview memberReview) {
        this.id = id;
        this.reviewFileName = reviewFileName;
        this.uploadFileName = uploadFileName;
        this.memberReview = memberReview;
    }
*/

}

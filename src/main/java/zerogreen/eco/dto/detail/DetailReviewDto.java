package zerogreen.eco.dto.detail;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
import zerogreen.eco.entity.detail.DetailReview;
import zerogreen.eco.entity.detail.ReviewImage;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.entity.userentity.VegetarianGrade;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class DetailReviewDto {

    private Long rno;
    @NotBlank
    private String reviewText;

    private Long parentReview;

    private Long id;
    private String username;
    private String nickname;
    private VegetarianGrade vegetarianGrade;

    private Long sno;
    private String storeName;

    private String storeThumbnail;

    private List<ReviewImage> imageList;
    private List<MultipartFile> imageFiles;
    private String reviewThumbnail;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdTime;
    private List<DetailReviewDto> nestedReviewList = new ArrayList<>();


    //필요한거니까 지우지말자..
    public DetailReviewDto() {}

    // 댓글 리스트 (Detail)
    public DetailReviewDto(DetailReview detailReview) {

        // 멤버 타입에 따라서 nickname 분기
        if (detailReview.getReviewer() instanceof Member) {
            Member member = (Member)detailReview.getReviewer();
            this.nickname = member.getNickname();
            this.vegetarianGrade = ((Member) detailReview.getReviewer()).getVegetarianGrade();

        } else if (detailReview.getReviewer() instanceof StoreMember) {
            StoreMember storeMember = (StoreMember) detailReview.getReviewer();
            this.nickname = storeMember.getStoreName();
        }

        // 자손 리스트 entity -> dto
        List<DetailReviewDto> collect =
                detailReview.getNestedReviewList().stream().map(DetailReviewDto::new).collect(Collectors.toList());
        this.rno = detailReview.getId();
        this.sno = detailReview.getStoreMember().getId();
        this.reviewText = detailReview.getReviewText();
        this.id = detailReview.getReviewer().getId();
        this.username = detailReview.getReviewer().getUsername();
        this.createdTime = detailReview.getModifiedDate();
        this.nestedReviewList = collect;
        this.storeName = detailReview.getStoreMember().getStoreName();
        this.imageList = detailReview.getImageList();
    }

    //리뷰 리스팅 (memberMyInfo)
    public DetailReviewDto(Long rno, String reviewText, Long sno, String storeName, Long id,
                           LocalDateTime createdTime, String storeThumbnail) {
        this.rno = rno;
        this.reviewText = reviewText;
        this.sno = sno;
        this.storeName = storeName;
        this.id = id;
        this.createdTime = createdTime;
        this.storeThumbnail = storeThumbnail;
    }
    
}

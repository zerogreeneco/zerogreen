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

    private Long id;
    private String username;
    private String nickname;

    private Long sno;
    private String storeName;

    private Long parentReview;

    private List<ReviewImage> imageList;
    private List<MultipartFile> imageFiles;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdTime;
    private List<DetailReviewDto> nestedReviewList = new ArrayList<>();


    public DetailReviewDto() {}

    // 댓글
    public DetailReviewDto(DetailReview detailReview) {

        // 멤버 타입에 따라서 nickname 분기
        if (detailReview.getReviewer() instanceof Member) {
            Member member = (Member)detailReview.getReviewer();
            this.nickname = member.getNickname();
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

    }
/*
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
*/


}

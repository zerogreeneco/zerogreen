package zerogreen.eco.entity.detail;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerogreen.eco.entity.baseentity.BaseTimeEntity;
import zerogreen.eco.entity.community.CommunityBoard;
import zerogreen.eco.entity.userentity.StoreMember;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class ReviewImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_image_id")
    private Long id;

    private String uploadFileName;
    private String reviewFileName;
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "review_id")
    private MemberReview memberReview;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id")
    private StoreMember storeMember;



    // 파일 폴더 저장
    public ReviewImage(String uploadFileName, String reviewFileName, String filePath) {
        this.uploadFileName = uploadFileName;
        this.reviewFileName = reviewFileName;
        this.filePath = filePath;
    }

    // 파일 DB 저장
    public ReviewImage(String uploadFileName, String reviewFileName, String filePath,
                       MemberReview memberReview, StoreMember storeMember) {
        this.uploadFileName = uploadFileName;
        this.reviewFileName = reviewFileName;
        this.filePath = filePath;
        this.memberReview = memberReview;
        this.storeMember = storeMember;
    }

    // 파일 DB 저장 (기존)
/*
    public ReviewImage(String uploadFileName, String reviewFileName, String filePath, MemberReview memberReview) {
        this.uploadFileName = uploadFileName;
        this.reviewFileName = reviewFileName;
        this.filePath = filePath;
        this.memberReview = memberReview;
    }
*/


}

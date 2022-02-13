package zerogreen.eco.entity.detail;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import zerogreen.eco.entity.baseentity.BaseTimeEntity;
import zerogreen.eco.entity.community.CommunityBoard;
import zerogreen.eco.entity.userentity.StoreMember;

import javax.persistence.*;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private DetailReview detailReview;

    @ManyToOne(fetch = FetchType.LAZY)
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
                       DetailReview detailReview, StoreMember storeMember) {
        this.uploadFileName = uploadFileName;
        this.reviewFileName = reviewFileName;
        this.filePath = filePath;
        this.detailReview = detailReview;
        this.storeMember = storeMember;
    }

}

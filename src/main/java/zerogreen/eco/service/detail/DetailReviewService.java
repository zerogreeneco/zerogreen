package zerogreen.eco.service.detail;

import zerogreen.eco.dto.detail.DetailReviewDto;
import zerogreen.eco.entity.detail.DetailReview;
import zerogreen.eco.entity.detail.ReviewImage;
import zerogreen.eco.entity.userentity.BasicUser;

import java.util.List;

public interface DetailReviewService {
    //save reviews
    Long saveImageReview(String reviewText, Long sno, BasicUser basicUser, List<ReviewImage> reviewImages);
    //void saveReview(String reviewText, Long sno, BasicUser basicUser);
    //Long saveReview(String reviewText, Long sno, BasicUser basicUser, List<ReviewImage> reviewImages);
    //Long saveReview(String reviewText, Long sno, BasicUser basicUser);


    //listing
    List<DetailReviewDto> findByStore(Long sno);
    //save comments
    void saveNestedReview(String reviewText, Long sno, BasicUser basicUser, Long rno);
    //edit reviews
    void modifyReview(DetailReviewDto detailReviewDto);
    //delete reviews
    void remove(Long id);
    //회원별 전체 리뷰 수 카운팅 (memberMyInfo)
    Long countReviewByUser(Long id);
    //회원별 리뷰남긴 가게 리스트 (memberMyInfo)
    List<DetailReviewDto> getReviewByUser(Long id);
    //가게별 멤버 리뷰 카운팅 (detail
    Long cntMemberReview(Long sno);
    //테스트 데이터
    Long saveReviewTest(DetailReview detailReview);
    Long saveNestedReviewTest(DetailReview detailReview, Long rno);

}

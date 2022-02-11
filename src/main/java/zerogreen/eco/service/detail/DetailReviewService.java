package zerogreen.eco.service.detail;

import zerogreen.eco.dto.detail.DetailReviewDto;
import zerogreen.eco.entity.detail.DetailReview;
import zerogreen.eco.entity.userentity.BasicUser;

import java.util.List;

public interface DetailReviewService {
    //save reviews
    void saveReview(String reviewText, Long sno, BasicUser basicUser);
    Long saveReviewTest(DetailReview detailReview);

    //temp list
    List<DetailReviewDto> findByStore(Long sno);


    //save comments
    //void saveNestedReview(String reviewText, Long sno, BasicUser basicUser, Long rno);
    //review list
    //Page<DetailReviewDto> getReviewList(Pageable pageable, Long sno);


    //회원별 전체 리뷰 수 카운팅 (memberMyInfo)
    Long countReviewByUser(Long id);
    //회원별 리뷰남긴 가게 리스트 (memberMyInfo)
    List<DetailReviewDto> getReviewByUser(Long id);
    //가게별 멤버 리뷰 카운팅 (detail
    Long cntMemberReview(Long sno);

    }

package zerogreen.eco.service.detail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zerogreen.eco.dto.detail.MemberReviewDto;
import zerogreen.eco.entity.detail.MemberReview;

public interface ReviewService {
    //멤버리뷰 DB저장
    Long saveReview(MemberReviewDto memberReviewDto);
    Long saveTest(MemberReview memberReview);
    //가게별 멤버 리뷰 카운팅
    Long cntMemberReview(MemberReviewDto memberReviewDto);
    //멤버리뷰 리스팅
    Page<MemberReview> getMemberReviewList(Pageable pageable, Long id);
    //멤버리뷰 삭제
    void remove(Long id);
    //멤버리뷰 수정
    void modifyReview(MemberReviewDto memberReviewDto);



    }

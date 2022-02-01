package zerogreen.eco.service.detail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zerogreen.eco.dto.detail.MemberReviewDto;

public interface ReviewService {
    //멤버리뷰 DB저장
    Long saveReview(MemberReviewDto memberReviewDto);
    //가게별 멤버 리뷰 카운팅
    Long cntMemberReview(MemberReviewDto memberReviewDto);
    //멤버리뷰 리스팅
    Page<MemberReviewDto> getMemberReviewList(Pageable pageable);
    //List<MemberReviewDto> getMemberReviewList(Long id);



    }

package zerogreen.eco.service.detail;

import zerogreen.eco.dto.detail.MemberReviewDto;

public interface ReviewService {
    //멤버 DB저장
    Long saveReview(MemberReviewDto memberReviewDto);


    }

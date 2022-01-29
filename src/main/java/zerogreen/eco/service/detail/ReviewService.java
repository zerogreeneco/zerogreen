package zerogreen.eco.service.detail;

import zerogreen.eco.dto.detail.MemberReviewDto;
import zerogreen.eco.entity.detail.MemberReview;

public interface ReviewService {
    //멤버 DB저장
    Long saveReview(String username, MemberReview memberReview);


    }

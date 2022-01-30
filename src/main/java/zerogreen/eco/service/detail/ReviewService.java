package zerogreen.eco.service.detail;

import zerogreen.eco.dto.detail.MemberReviewDto;
import zerogreen.eco.entity.detail.MemberReview;
import zerogreen.eco.entity.userentity.StoreMember;

public interface ReviewService {
    //멤버 DB저장
    Long saveReview(String username, Long id, MemberReview memberReview);


    }

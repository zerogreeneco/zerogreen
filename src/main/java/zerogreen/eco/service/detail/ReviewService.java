package zerogreen.eco.service.detail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zerogreen.eco.dto.detail.MemberReviewDto;
import zerogreen.eco.entity.detail.MemberReview;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.StoreMember;

public interface ReviewService {
    //멤버리뷰 DB저장
    Long saveReview(MemberReviewDto memberReviewDto);
    //멤버리뷰 리스팅
    Page<MemberReviewDto> getMemberReviewList(Pageable pageable);


    }

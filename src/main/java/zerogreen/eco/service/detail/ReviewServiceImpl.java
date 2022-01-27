package zerogreen.eco.service.detail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zerogreen.eco.dto.detail.MemberReviewDto;
import zerogreen.eco.entity.detail.MemberReview;
import zerogreen.eco.repository.detail.MemberReviewRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final MemberReviewRepository memberReviewRepository;

    //멤버리뷰 DB저장
    @Override
    public Long saveReview(MemberReviewDto memberReviewDto) {
        MemberReview memberReview = new MemberReview(memberReviewDto.getId(), memberReviewDto.getRno(), memberReviewDto.getReviewText(), memberReviewDto.getStoreName());
        memberReviewRepository.save(memberReview);
        return memberReview.getRno();
    }
}

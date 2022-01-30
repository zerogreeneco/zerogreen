package zerogreen.eco.service.detail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.dto.detail.MemberReviewDto;
import zerogreen.eco.entity.detail.MemberReview;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.repository.detail.MemberReviewRepository;
import zerogreen.eco.repository.user.BasicUserRepository;
import zerogreen.eco.repository.user.StoreMemberRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final MemberReviewRepository memberReviewRepository;
    private final StoreMemberRepository storeMemberRepository;
    private final BasicUserRepository basicUserRepository;

    //멤버리뷰 DB저장
    @Override
    @Transactional
    public Long saveReview(MemberReviewDto memberReviewDto) {
        BasicUser findUser = basicUserRepository.findByUsername(memberReviewDto.getUsername()).orElseThrow();
        StoreMember findStore = storeMemberRepository.findById(memberReviewDto.getId()).orElseThrow();

        MemberReview memberReview = memberReviewRepository.save( new MemberReview(memberReviewDto.getReviewText(),
                        findUser, findStore));

        return memberReview.getId();
    }

    //리뷰 리스트
    @Override
    public Page<MemberReviewDto> getMemberReviewList(Pageable pageable) {
        return memberReviewRepository.findByStore(pageable);
    }

}

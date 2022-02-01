package zerogreen.eco.service.detail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.dto.detail.LikesDto;
import zerogreen.eco.dto.detail.MemberReviewDto;
import zerogreen.eco.entity.detail.MemberReview;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.repository.detail.MemberReviewRepository;
import zerogreen.eco.repository.user.BasicUserRepository;
import zerogreen.eco.repository.user.StoreMemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    //가게별 멤버 리뷰 수 카운팅
    @Override
    public Long cntMemberReview(MemberReviewDto memberReviewDto) {
        StoreMember findStore = storeMemberRepository.findById(memberReviewDto.getId()).orElseThrow();
        return memberReviewRepository.counting(findStore);
    }


    //멤버 리뷰 리스트 ** 작업 중 **
    @Override
    public Page<MemberReviewDto> getMemberReviewList(Pageable pageable) {
        return memberReviewRepository.findByStore(pageable);
    }

/*
    public List<MemberReviewDto> getMemberReviewList(Long id) {
        StoreMember findStore = storeMemberRepository.findById(id).orElseThrow();
        List<MemberReview> result = memberReviewRepository.findByStore(findStore);
        return result.stream().map(memberReview ->
                new MemberReviewDto(memberReview.getId(), memberReview.getReviewText(),
                        memberReview.getBasicUser().getUsername(),
                        memberReview.getStoreMember().getId())).collect(Collectors.toList());
    }
*/
}

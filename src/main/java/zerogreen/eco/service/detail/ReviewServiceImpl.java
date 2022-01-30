package zerogreen.eco.service.detail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
    public Long saveReview(MemberReviewDto memberReviewDto) {
        MemberReview memberReview = memberReviewRepository.save(
                new MemberReview(memberReviewDto.getReviewText(), memberReviewDto.getBasicUser(),
                        memberReviewDto.getStoreMember()));
        log.info("zzzzzzzzzzz5555555"+memberReview);
        log.info("zzzzzzzzzzz6666666"+memberReviewDto.getReviewText());
        log.info("zzzzzzzzzzz7777777"+memberReviewDto.getUsername());
        log.info("zzzzzzzzzzz8888888"+memberReviewDto.getId());
                return memberReview.getId();
    }

        /*
        return memberReviewRepository.save(new MemberReview(memberReview.getReviewText(),
                        memberReview.getBasicUser().getUsername(),
                        memberReview.getBasicUser().getId()))
                .getRno();
*/

//        MemberReview memberReview = new MemberReview(memberReviewDto.getReviewText(),
//                memberReviewDto.getStoreName(),memberReviewDto.getUsername());
//        memberReviewRepository.save(memberReview);
//        return memberReview.getRno();

}

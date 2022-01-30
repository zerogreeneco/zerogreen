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
    public Long saveReview(String username, MemberReview memberReview) {
/*        log.info("--------44444444"+memberReview);
        BasicUser findUser = basicUserRepository.findByUsername(username).orElseThrow();
        log.info("-----------3333333"+findUser.getUsername());
        return memberReviewRepository.save(new MemberReview(memberReview.getReviewText(),
                        findUser))
                .getRno();*/
        return null;
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

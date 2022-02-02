package zerogreen.eco.service.detail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.dto.detail.LikesDto;
import zerogreen.eco.dto.detail.MemberReviewDto;
import zerogreen.eco.dto.detail.StoreReviewDto;
import zerogreen.eco.entity.detail.MemberReview;
import zerogreen.eco.entity.detail.StoreReview;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.repository.detail.MemberReviewRepository;
import zerogreen.eco.repository.detail.StoreReviewRepository;
import zerogreen.eco.repository.user.BasicUserRepository;
import zerogreen.eco.repository.user.StoreMemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final MemberReviewRepository memberReviewRepository;
    private final StoreMemberRepository storeMemberRepository;
    private final BasicUserRepository basicUserRepository;
    private final StoreReviewRepository storeReviewRepository;

    //멤버리뷰 DB저장(db저장은 엔티티)
    @Override
    @Transactional
    public Long saveReview(MemberReviewDto memberReviewDto) {
        BasicUser findUser = basicUserRepository.findByUsername(memberReviewDto.getUsername()).orElseThrow();
        StoreMember findStore = storeMemberRepository.findById(memberReviewDto.getId()).orElseThrow();

        return memberReviewRepository.save( new MemberReview(memberReviewDto.getReviewText(),
                        findUser, findStore))
                .getId();
    }

    //멤버리뷰 테스트 데이터 저장
    @Override
    @Transactional
    public Long saveTest(MemberReview memberReview) {
        BasicUser findUser = basicUserRepository.findByUsername(memberReview.getBasicUser().getUsername()).orElseThrow();
        StoreMember findStore = storeMemberRepository.findById(memberReview.getStoreMember().getId()).orElseThrow();

        return memberReviewRepository.save( new MemberReview(memberReview.getReviewText(),
                        findUser, findStore))
                .getId();
    }


    //가게별 멤버 리뷰 수 카운팅
    @Override
    public Long cntMemberReview(MemberReviewDto memberReviewDto) {
        StoreMember findStore = storeMemberRepository.findById(memberReviewDto.getId()).orElseThrow();
        return memberReviewRepository.counting(findStore);
    }

    //멤버 리뷰 리스트
    @Override
    public Page<MemberReview> getMemberReviewList(Pageable pageable, Long id) {
        StoreMember findStore = storeMemberRepository.findById(id).orElseThrow();
        return memberReviewRepository.findByStore(pageable, findStore);
    }

    //멤버리뷰 삭제
    @Override
    public void remove(Long id) {
        memberReviewRepository.deleteById(id);
    }

    //멤버리뷰 수정 ** 컨트롤러 수정하면서 같이 수정 예정**
    @Override
    public void modifyReview(MemberReviewDto memberReviewDto) {
        Optional<MemberReview> result = memberReviewRepository.findById(memberReviewDto.getRno());
        if(result.isPresent()) {
            MemberReview review = result.get();
            review.editMemberReview(memberReviewDto.getReviewText());
            memberReviewRepository.save(review);
        }
    }

    //스토어멤버 리뷰 DB저장
    @Override
    @Transactional
    public Long saveStoreReview(StoreReviewDto storeReviewDto) {
        StoreMember findStore = storeMemberRepository.findById(storeReviewDto.getSno()).orElseThrow();
        MemberReview findReview = memberReviewRepository.findById(storeReviewDto.getRno()).orElseThrow();
        log.info("qqqqq6666: "+ findStore);
        log.info("qqqqq7777: "+ findReview);
        return storeReviewRepository.save( new StoreReview(storeReviewDto.getStoreReviewText(),
                        findStore, findReview))
                .getId();
    }


}

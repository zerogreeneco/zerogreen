package zerogreen.eco.service.detail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zerogreen.eco.repository.detail.MemberReviewRepository;
import zerogreen.eco.repository.detail.ReviewImageRepository;
import zerogreen.eco.repository.detail.StoreReviewRepository;
import zerogreen.eco.repository.user.BasicUserRepository;
import zerogreen.eco.repository.user.StoreMemberRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final MemberReviewRepository memberReviewRepository;
    private final StoreMemberRepository storeMemberRepository;
    private final BasicUserRepository basicUserRepository;
    private final StoreReviewRepository storeReviewRepository;
    private final ReviewImageRepository reviewImageRepository;

    //멤버리뷰 DB저장 (이미지 포함)
/*
    @Override
    @Transactional
    public Long saveReview(ReviewRequestDto reviewRequestDto, BasicUser basicUser, Long sno, List<ReviewImage> reviewImages) {
        StoreMember findStore = storeMemberRepository.findById(sno).orElseThrow();
        MemberReview saveReview = memberReviewRepository.save( new MemberReview(reviewRequestDto.getReviewText(),
                        basicUser, findStore));

        if(reviewImages.size() != 0) {
            for (ReviewImage image : reviewImages) {
                log.info("aaaaaaaareviewImage " + image);

                reviewImageRepository.save(new ReviewImage(
                        image.getUploadFileName(), image.getReviewFileName(), image.getFilePath(), saveReview,findStore));
            }
        }
        return saveReview.getId();
    }

    //기존 save db
*/
/*
    @Override
    @Transactional
    public Long saveReview(MemberReviewDto memberReviewDto, List<ReviewImage> reviewImages) {
        BasicUser findUser = basicUserRepository.findByUsername(memberReviewDto.getUsername()).orElseThrow();
        StoreMember findStore = storeMemberRepository.findById(memberReviewDto.getId()).orElseThrow();

        MemberReview saveReview = memberReviewRepository.save( new MemberReview(memberReviewDto.getReviewText(),
                        findUser, findStore));

        memberReviewRepository.flush();
        log.info("aaaaaaaaSaveTextReview " + saveReview);

        if(reviewImages.size() != 0) {
            for (ReviewImage image : reviewImages) {
                log.info("aaaaaaaareviewImage " + image);

                reviewImageRepository.save(new ReviewImage(
                        image.getUploadFileName(), image.getReviewFileName(), image.getFilePath(), saveReview));
            }
        }
        return saveReview.getId();
    }
*//*


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


    //멤버 리뷰 리스트
*/
/*
    @Override
    public Page<MemberReviewDto> getMemberReviewList(Pageable pageable, Long sno) {
        StoreMember findStore = storeMemberRepository.findById(sno).orElseThrow();
        return memberReviewRepository.findByStore(pageable, findStore);
    }
*//*

*/
/*
    public List<MemberReviewDto> getMemberReviewList(Long sno) {
        log.info("~~~~~~66 " + sno);
        List<MemberReview> result = memberReviewRepository.findByStore(sno);
        log.info("~~~~~77 " + result);

        return result.stream().map(MemberReviewDto::new).collect(Collectors.toList());
    }
*//*


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
        return storeReviewRepository.save( new StoreReview(storeReviewDto.getStoreReviewText(),
                        findStore, findReview))
                .getId();
    }

    //스토어멤버 리뷰 테스트 데이터
    @Override
    @Transactional
    public Long ssrT(StoreReview storeReview) {
        StoreMember findStore = storeMemberRepository.findById(storeReview.getStoreMember().getId()).orElseThrow();
        MemberReview findReview = memberReviewRepository.findById(storeReview.getMemberReview().getId()).orElseThrow();
        return storeReviewRepository.save( new StoreReview(storeReview.getStoreReviewText(),
                        findStore, findReview))
                .getId();
    }

    //스토어멤버 리뷰 삭제
    @Override
    public void deleteStoreReview(Long id) {
        storeReviewRepository.deleteById(id);
    }

    //스토어멤버 리뷰 수정 ** 컨트롤러 수정하면서 같이 수정 예정**
    @Override
    public void modifyStoreReview(StoreReviewDto storeReviewDto) {
        Optional<StoreReview> result = storeReviewRepository.findById(storeReviewDto.getSrno());
        if(result.isPresent()) {
            StoreReview review = result.get();
            review.editStoreReview(storeReviewDto.getStoreReviewText());
            storeReviewRepository.save(review);
        }
    }

*/

}

package zerogreen.eco.service.detail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zerogreen.eco.repository.detail.ReviewImageRepository;
import zerogreen.eco.repository.user.BasicUserRepository;
import zerogreen.eco.repository.user.StoreMemberRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final StoreMemberRepository storeMemberRepository;
    private final BasicUserRepository basicUserRepository;
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
*/

}

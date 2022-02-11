package zerogreen.eco.service.detail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.dto.detail.DetailReviewDto;
import zerogreen.eco.entity.detail.DetailReview;
import zerogreen.eco.entity.detail.ReviewImage;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.repository.detail.DetailReviewRepository;
import zerogreen.eco.repository.detail.MemberReviewRepository;
import zerogreen.eco.repository.detail.ReviewImageRepository;
import zerogreen.eco.repository.detail.StoreReviewRepository;
import zerogreen.eco.repository.user.BasicUserRepository;
import zerogreen.eco.repository.user.StoreMemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DetailReviewServiceImpl implements DetailReviewService {
    private final MemberReviewRepository memberReviewRepository;
    private final StoreMemberRepository storeMemberRepository;
    private final BasicUserRepository basicUserRepository;
    private final StoreReviewRepository storeReviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final DetailReviewRepository detailReviewRepository;

    //save reviews
    @Override
    @Transactional
    public Long saveReview(String reviewText, Long sno, BasicUser basicUser) {
        StoreMember storeMember = storeMemberRepository.findById(sno).orElseThrow();
        DetailReview saveReview = detailReviewRepository.save(new DetailReview(reviewText, basicUser, storeMember));// 생성자 없음 왜요,,?

/*
        if(reviewImages.size() != 0) {
            for (ReviewImage image : reviewImages) {
                log.info("aaaaaaaareviewImage " + image);

                reviewImageRepository.save(new ReviewImage(
                        image.getUploadFileName(), image.getReviewFileName(), image.getFilePath(), saveReview, storeMember));
            }
        }
*/

        return saveReview.getId();

    }

    //Test
    @Override
    @Transactional
    public Long saveReviewTest(DetailReview detailReview) {
        StoreMember storeMember = storeMemberRepository.findById(detailReview.getStoreMember().getId()).orElseThrow();
        return detailReviewRepository.save(new DetailReview(detailReview.getReviewText(), detailReview.getReviewer(), storeMember))
                .getId(); // 생성자 없음 왜요,,?
    }

    //리스팅 작업중
    @Override
    public List<DetailReviewDto> findByStore(Long sno) {
        List<DetailReview> reviewList = detailReviewRepository.findByStore(sno);
        return reviewList.stream().map(DetailReviewDto::new).collect(Collectors.toList());
    }


    //save comments
/*
    @Override
    @Transactional
    public void saveNestedReview(String reviewText, Long sno, BasicUser basicUser, Long rno) {
        StoreMember storeMember = storeMemberRepository.findById(sno).orElseThrow();
        // 컨트롤러에서 넘어 온 부모 댓글의 PK로 해당 부모 댓글을 검색
        DetailReview parentReview = detailReviewRepository.findById(rno).orElseThrow();
        // 새로운 자식 댓글을 생성하고 저장
        DetailReview childReview = new DetailReview(reviewText, basicUser, storeMember);
        detailReviewRepository.save(childReview);
        // 부모 댓글에 자식 댓글을 리스트로 저장 (양방향 매핑)
        parentReview.addNestedReview(childReview);
    }
*/

    //list of reviews
/*
    @Override
    public Page<DetailReviewDto> getReviewList(Pageable pageable, Long sno) {
        return detailReviewRepository.findByStore(pageable, sno);
    }
*/

    //회원별 전체 리뷰 수 카운팅 (memberMyInfo)
    @Override
    public Long countReviewByUser(Long id) {
        return detailReviewRepository.countReviewByUser(id);
    }

    //회원별 리뷰남긴 가게 리스트 (memberMyInfo)
    @Override
    public List<DetailReviewDto> getReviewByUser(Long id) {
        List<DetailReview> result = detailReviewRepository.getReviewByUser(id);
        return result.stream().map(DetailReviewDto::new).collect(Collectors.toList());
    }

    //가게별 멤버 리뷰 수 카운팅 (deatil)
    @Override
    public Long cntMemberReview(Long sno) {
        return detailReviewRepository.counting(sno);
    }



}

package zerogreen.eco.service.detail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.dto.detail.DetailReviewDto;
import zerogreen.eco.dto.detail.ReviewImageDto;
import zerogreen.eco.entity.detail.DetailReview;
import zerogreen.eco.entity.detail.ReviewImage;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.repository.detail.DetailReviewRepository;
import zerogreen.eco.repository.detail.ReviewImageRepository;
import zerogreen.eco.repository.user.StoreMemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DetailReviewServiceImpl implements DetailReviewService {
    private final StoreMemberRepository storeMemberRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final DetailReviewRepository detailReviewRepository;

    //save reviews
/*
    @Override
    @Transactional
    public Long saveReview(String reviewText, Long sno, BasicUser basicUser) {
        StoreMember storeMember = storeMemberRepository.findById(sno).orElseThrow();
        DetailReview saveReview = detailReviewRepository.save(new DetailReview(reviewText, basicUser, storeMember));// 생성자 없음 왜요,,?
*/
/*
        if(reviewImages.size() != 0) {
            for (ReviewImage image : reviewImages) {
                log.info("aaaaaaaareviewImage " + image);

                reviewImageRepository.save(new ReviewImage(
                        image.getUploadFileName(), image.getReviewFileName(), image.getFilePath(), saveReview, storeMember));
            }
        }
*//*

        return saveReview.getId();
    }
*/

//멤버리뷰 DB저장 (이미지 포함)
    @Override
    @Transactional
    public Long saveImageReview(String reviewText, Long sno, BasicUser basicUser, List<ReviewImage> reviewImages) {
        StoreMember storeMember = storeMemberRepository.findById(sno).orElseThrow();
        DetailReview saveReview = detailReviewRepository.save(new DetailReview(reviewText, basicUser, storeMember));

        if(reviewImages.size() != 0) {
            for (ReviewImage image : reviewImages) {
                log.info("aaaaaaaareviewImage " + image);

                reviewImageRepository.save(new ReviewImage(
                        image.getUploadFileName(), image.getReviewFileName(), image.getFilePath(), saveReview, storeMember));
            }
        }
        return saveReview.getId();
    }



    //리스팅
    @Override
    public List<DetailReviewDto> findByStore(Long sno) {
        List<DetailReview> reviewList = detailReviewRepository.findByStore(sno);
        return reviewList.stream().map(DetailReviewDto::new).collect(Collectors.toList());
    }
/*
    @Override
    public List<DetailReviewDto> findByStore(Long sno, Long rno) {
        List<DetailReview> reviewList = detailReviewRepository.findByStore(sno);

        List<ReviewImageDto> reviewImage = reviewImageRepository.findByReview(rno).stream().map(ReviewImageDto::new).collect(Collectors.toList());
        int listSize = reviewImage.size();
        String arr[] = reviewImage.toArray(new String[listSize]);

        return reviewList.stream().map(DetailReviewDto::new).collect(Collectors.toList());
    }
*/


    //대댓글
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

    //리뷰 수정
    @Override
    public void modifyReview(DetailReviewDto detailReviewDto) {
        Optional<DetailReview> result = detailReviewRepository.findById(detailReviewDto.getRno());
        if(result.isPresent()) {
            DetailReview review = result.get();
            review.editReview(detailReviewDto.getReviewText());
            detailReviewRepository.save(review);
        }
    }

    //리뷰 삭제
    @Override
    public void remove(Long rno) {
        detailReviewRepository.deleteById(rno);
    }

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

    //가게별 멤버 리뷰 수 카운팅 (detail)
    @Override
    public Long cntMemberReview(Long sno) {
        return detailReviewRepository.counting(sno);
    }

    //Test
    @Override
    @Transactional
    public Long saveReviewTest(DetailReview detailReview) {
        StoreMember storeMember = storeMemberRepository.findById(detailReview.getStoreMember().getId()).orElseThrow();
        return detailReviewRepository.save(new DetailReview(detailReview.getReviewText(), detailReview.getReviewer(), storeMember))
                .getId(); // 생성자 없음 왜요,,?
    }

    //save comments Test
    @Override
    @Transactional
    public Long saveNestedReviewTest(DetailReview detailReview, Long rno) {
        StoreMember storeMember = storeMemberRepository.findById(detailReview.getStoreMember().getId()).orElseThrow();
        DetailReview parentReview = detailReviewRepository.findById(rno).orElseThrow();
        DetailReview childReview = new DetailReview(detailReview.getReviewText(), detailReview.getReviewer(), storeMember);
        detailReviewRepository.save(childReview);
        parentReview.addNestedReview(childReview);
        return childReview.getId();
    }


}

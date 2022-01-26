package zerogreen.eco.service.detail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zerogreen.eco.dto.detail.ReviewDto;
import zerogreen.eco.entity.detail.Review;
import zerogreen.eco.repository.detail.ReviewRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;

    //리뷰 DB저장
    @Override
    public Long saveReview(ReviewDto reviewDto) {
        Review review = new Review(reviewDto.getUsername(),reviewDto.getPhoneNumber(),reviewDto.getPassword(),reviewDto.getUserRole(),
                reviewDto.getRno(),reviewDto.getReviewText(),reviewDto.getNickname(),reviewDto.getStoreName());
        reviewRepository.save(review);
        return review.getRno();
    }
}

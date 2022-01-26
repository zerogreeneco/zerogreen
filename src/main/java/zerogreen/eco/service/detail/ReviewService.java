package zerogreen.eco.service.detail;

import zerogreen.eco.dto.detail.ReviewDto;

public interface ReviewService {
    //DB저장
    Long saveReview(ReviewDto reviewDto);


    }

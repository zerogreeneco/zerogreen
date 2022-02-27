package zerogreen.eco.service.detail;

import zerogreen.eco.dto.detail.ReviewImageDto;

import java.util.List;

public interface ReviewImageService {

    //listing
    List<ReviewImageDto> findByStore(Long sno);
    //delete review Images
    void deleteReviewImage(Long id, String filePath, String thumbnail);

}

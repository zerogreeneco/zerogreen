package zerogreen.eco.service.detail;

import org.springframework.web.multipart.MultipartFile;
import zerogreen.eco.dto.detail.ReviewImageDto;
import zerogreen.eco.entity.detail.DetailReview;
import zerogreen.eco.entity.detail.ReviewImage;

import java.io.IOException;
import java.util.List;

public interface ReviewImageService {
    String getFullPath(String filename);
    ReviewImage saveReviewImage(MultipartFile multipartFile) throws IOException;
    List<ReviewImage> reviewImageFiles(List<MultipartFile> multipartFiles) throws IOException;

    //listing
    List<ReviewImageDto> findByStore(Long sno);

    //리뷰별 리스팅
//    List<ReviewImageDto> findByReview(DetailReview detailReview);
    List<ReviewImageDto> findByReview(Long rno);


    //delete review Images
    void deleteReviewImage(Long id, String filePath);

    }

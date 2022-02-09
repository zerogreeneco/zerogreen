package zerogreen.eco.service.detail;

import org.springframework.web.multipart.MultipartFile;
import zerogreen.eco.dto.detail.MemberReviewDto;
import zerogreen.eco.entity.detail.ReviewImage;

import java.io.IOException;
import java.util.List;

public interface ReviewImageService {
    String getFullPath(String filename);
    ReviewImage saveReviewImage(MultipartFile multipartFile) throws IOException;
    List<ReviewImage> reviewImageFiles(List<MultipartFile> multipartFiles) throws IOException;


    }

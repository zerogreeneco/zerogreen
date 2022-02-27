package zerogreen.eco.service.detail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import zerogreen.eco.dto.detail.ReviewImageDto;
import zerogreen.eco.entity.detail.ReviewImage;
import zerogreen.eco.repository.detail.ReviewImageRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewImageServiceImpl implements ReviewImageService {

    private final ReviewImageRepository reviewImageRepository;

    //List of Images only..
    @Override
    public List<ReviewImageDto> findByStore(Long sno) {
        return reviewImageRepository.findByStore(sno).stream().map(ReviewImageDto::new).collect(Collectors.toList());
    }

    // delete images
    @Override
    public void deleteReviewImage(Long id, String filePath, String thumbnail) {
        File file = new File(filePath);
        File file2 = new File(thumbnail);

        try {
            if (file.exists()) {
                boolean delete = file.delete();
                if (delete) {
                    file2.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("파일 삭제에 실패했습니다.");
        }
    }


}

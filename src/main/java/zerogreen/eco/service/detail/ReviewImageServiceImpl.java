package zerogreen.eco.service.detail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import zerogreen.eco.entity.community.BoardImage;
import zerogreen.eco.entity.detail.ReviewImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewImageServiceImpl implements ReviewImageService {

    //완성 후 중복코드 삭제예정

    @Value("${file.dir}")
    private String fileDir;

    @Value("C:/imageUpload/")
    private String imageFileDir;

    private final String[] EXTENSION = {"image/gif", "image/jpeg", "image/png", "image/bmp", "application/pdf"};

    @Override
    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    /*
     * 서버에 저장될 이름 생성
     * */
    private String createStoreFilename(String originalFilename) {
        String ext = extractExt(originalFilename);
        //uuid
        String uuid = UUID.randomUUID().toString();
        // uuid.확장자 형태로 저장
        return uuid + "." + ext;
    }

    /*
     * 확장자만 따로 뽑기
     * */
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }


    //이미지 업로드
    @Override
    public ReviewImage saveReviewImage(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String reviewFileName = createStoreFilename(originalFilename);
        multipartFile.transferTo(new File(getFullPath(reviewFileName)));

        return new ReviewImage(originalFilename, reviewFileName, getFullPath(reviewFileName));

    }

    //얘는 뭐하는거지.. 파일 저장..?
    @Override
    public List<ReviewImage> reviewImageFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<ReviewImage> reviewImages = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                reviewImages.add(saveReviewImage(multipartFile));
            }
        }
        return reviewImages;
    }




}

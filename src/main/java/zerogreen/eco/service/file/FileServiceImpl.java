package zerogreen.eco.service.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import zerogreen.eco.entity.file.RegisterFile;
import zerogreen.eco.entity.file.StoreImageFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{

    @Value("${file.dir}")
    private String fileDir;

    @Value("C:/imageUpload/")
    private String imageFileDir;

    private final String[] EXTENSION = {"image/gif", "image/jpeg", "image/png", "image/bmp", "application/pdf"};

    @Override
    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    @Override
    public String getFullPathImage(String filename, String storeName) {
        return imageFileDir + storeName  + filename;
    }

    /*
    * 다수의 파일 저장 (이미지)
    * */
    @Override
    public List<StoreImageFile> storeImageFiles(List<MultipartFile> multipartFiles, String storeName) throws IOException {

        List<StoreImageFile> storeImageFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeImageFileResult.add(saveImageFile(multipartFile, storeName));
            }
        }
        return storeImageFileResult;
    }

    /*
    * 단일 파일 저장(사업자 등록증)
    * */
    @Override
    public RegisterFile saveFile(MultipartFile multipartFile) throws IOException {

        if (multipartFile.isEmpty()) {
            return null;
        }
        // 사용자가 저장한 파일 이름
        String originalFilename = multipartFile.getOriginalFilename();
        log.info("SERVICE ORIGIN NAME={}", originalFilename);
        log.info("SERVICE ORIGIN NAME={}", extractExt(originalFilename));

        String storeFilename = createStoreFilename(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFilename)));

        return new RegisterFile(originalFilename, storeFilename, getFullPath(storeFilename));

    }

    /*
    * 단일 파일 저장 (이미지)
    * */
    @Override
    public StoreImageFile saveImageFile(MultipartFile multipartFile, String storeName) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        // 사용자가 저장한 파일 이름
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);
        multipartFile.transferTo(new File(getFullPathImage(storeFilename, storeName)));

        return new StoreImageFile(originalFilename, storeFilename, getFullPathImage(storeFilename, storeName));
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

    public void imageResize(String storeName) throws IOException {
        File file = new File(getFullPath(storeName));
        InputStream inputStream = new FileInputStream(file);
        Image image = new ImageIcon(file.toString()).getImage();

        int width = 1280;
        int height = 720;

        BufferedImage resizedImage = resize(inputStream, width, height);

        ImageIO.write(resizedImage, "jpg", new File(getFullPath("resize_" + storeName)));
    }

    private BufferedImage resize(InputStream inputStream, int width, int height) throws IOException {

        BufferedImage inputImage = ImageIO.read(inputStream);

        BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType());

        Graphics2D graphics2D = outputImage.createGraphics();
        graphics2D.drawImage(inputImage, 0, 0, width, height, null);
        graphics2D.dispose();

        return outputImage;
    }
}

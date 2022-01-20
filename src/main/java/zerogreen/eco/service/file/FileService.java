package zerogreen.eco.service.file;

import org.springframework.web.multipart.MultipartFile;
import zerogreen.eco.entity.file.RegisterFile;
import zerogreen.eco.entity.file.StoreImageFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    String getFullPath(String filename);

    RegisterFile saveFile(MultipartFile multipartFile) throws IOException;
    StoreImageFile saveImageFile(MultipartFile multipartFile) throws IOException;

    List<StoreImageFile> storeImageFiles(List<MultipartFile> multipartFiles) throws IOException;
}

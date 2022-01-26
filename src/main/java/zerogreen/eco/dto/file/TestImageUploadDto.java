package zerogreen.eco.dto.file;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class TestImageUploadDto {

    private Long fileId;
    private String filename;
    private String storeName;
    private List<MultipartFile> files;

}

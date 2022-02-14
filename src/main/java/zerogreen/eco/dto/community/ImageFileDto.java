package zerogreen.eco.dto.community;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import zerogreen.eco.entity.community.BoardImage;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ImageFileDto {

    private Long id;
    private String storeFileName;
    private String uploadFileName;
    private String filePath;

    public ImageFileDto(BoardImage image) {
        this.id = image.getId();
        this.storeFileName = "thumb_" + image.getStoreFileName();
        this.uploadFileName = image.getUploadFileName();
        this.filePath = image.getFilePath();
    }
}

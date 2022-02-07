package zerogreen.eco.dto.community;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerogreen.eco.entity.community.BoardImage;

@Getter
@Setter
@NoArgsConstructor
public class ImageFileDto {

    private Long id;
    private String storeFileName;
    private String uploadFileName;

    public ImageFileDto(BoardImage image) {
        this.id = image.getId();
        this.storeFileName = image.getStoreFileName();
        this.uploadFileName = image.getUploadFileName();
    }
}
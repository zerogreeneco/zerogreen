package zerogreen.eco.service.community;

import zerogreen.eco.dto.community.ImageFileDto;

import java.util.List;

public interface BoardImageService {

    List<ImageFileDto> findByBoardId(Long boardId);

    void modifyImage(Long boardId, String storeName, String originalName, String path);

    void deleteImage(Long imageId, String filePath);
}

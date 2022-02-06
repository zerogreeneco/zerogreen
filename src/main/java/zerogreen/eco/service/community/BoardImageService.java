package zerogreen.eco.service.community;

import zerogreen.eco.dto.community.ImageFileDto;

import java.util.List;

public interface BoardImageService {

    List<ImageFileDto> findByBoardId(Long boardId);
}

package zerogreen.eco.service.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zerogreen.eco.dto.community.ImageFileDto;
import zerogreen.eco.entity.community.BoardImage;
import zerogreen.eco.entity.community.CommunityBoard;
import zerogreen.eco.repository.community.BoardImageRepository;
import zerogreen.eco.repository.community.CommunityBoardRepository;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardImageServiceImpl implements BoardImageService {

    private final BoardImageRepository boardImageRepository;
    private final CommunityBoardRepository boardRepository;

    @Override
    public List<ImageFileDto> findByBoardId(Long boardId) {
        return boardImageRepository.findByBoard(boardId).stream().map(ImageFileDto::new).collect(Collectors.toList());
    }

    // 이미지 추가
    @Override
    public void modifyImage(Long boardId, String storeName, String originalName, String path) {
        CommunityBoard communityBoard = boardRepository.findById(boardId).orElseThrow();

        boardImageRepository.save(new BoardImage(originalName, storeName, path, communityBoard));
    }

    // 이미지 삭제
    @Override
    public void deleteImage(Long imageId, String filePath) {
        File file = new File(filePath);

        try {
            if (file.exists()) {
                file.delete();
                boardImageRepository.deleteById(imageId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("파일 삭제에 실패했습니다.");
        }

    }
}

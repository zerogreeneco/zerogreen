package zerogreen.eco.service.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zerogreen.eco.dto.community.ImageFileDto;
import zerogreen.eco.entity.community.BoardImage;
import zerogreen.eco.entity.community.CommunityBoard;
import zerogreen.eco.repository.community.BoardImageRepository;
import zerogreen.eco.repository.community.CommunityBoardRepository;

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

    @Override
    public void modifyImage(Long boardId, String storeName, String originalName, String path) {
        CommunityBoard communityBoard = boardRepository.findById(boardId).orElseThrow();

        boardImageRepository.save(new BoardImage(originalName, storeName, path, communityBoard));
    }

    @Override
    public void deleteImage(Long imageId) {

    }
}

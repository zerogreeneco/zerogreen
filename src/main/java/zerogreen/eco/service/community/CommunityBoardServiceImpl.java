package zerogreen.eco.service.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.dto.community.CommunityRequestDto;
import zerogreen.eco.entity.community.BoardImage;
import zerogreen.eco.entity.community.CommunityBoard;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.repository.community.BoardImageRepository;
import zerogreen.eco.repository.community.CommunityBoardRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityBoardServiceImpl implements CommunityBoardService {

    private final CommunityBoardRepository boardRepository;
    private final BoardImageRepository boardImageRepository;

    @Override
    @Transactional
    public void boardRegister(CommunityRequestDto dto, Member writer, List<BoardImage> imageList) {

        CommunityBoard saveBoard = boardRepository.save(CommunityBoard.builder()
                .title(dto.getTitle())
                .text(dto.getText())
                .category(dto.getCategory())
                .member(writer)
                .build());
        boardRepository.flush();

        if (imageList.size() != 0) {
            List<BoardImage> boardImages = new ArrayList<>();
            for (BoardImage boardImage : imageList) {
                boardImageRepository.save(new BoardImage(
                        boardImage.getUploadFileName(), boardImage.getStoreFileName(), boardImage.getFilePath(),saveBoard));
            }
        }

    }
}

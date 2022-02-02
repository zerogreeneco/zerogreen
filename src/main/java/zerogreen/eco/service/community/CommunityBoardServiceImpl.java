package zerogreen.eco.service.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.dto.community.CommunityRequestDto;
import zerogreen.eco.dto.community.CommunityResponseDto;
import zerogreen.eco.entity.community.BoardImage;
import zerogreen.eco.entity.community.Category;
import zerogreen.eco.entity.community.CommunityBoard;
import zerogreen.eco.entity.community.CommunityLike;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.repository.community.BoardImageRepository;
import zerogreen.eco.repository.community.CommunityBoardRepository;
import zerogreen.eco.repository.community.CommunityLikeRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityBoardServiceImpl implements CommunityBoardService {

    private final CommunityBoardRepository boardRepository;
    private final BoardImageRepository boardImageRepository;
    private final CommunityLikeRepository communityLikeRepository;

    /*
    * 게시글 저장
    * */
    @Override
    @Transactional
    public void boardRegister(CommunityRequestDto dto, Member writer, List<BoardImage> imageList) {

        CommunityBoard saveBoard = boardRepository.save(CommunityBoard.builder()
                .title(dto.getTitle())
                .text(dto.getText())
                .category(dto.getCategory())
                .member(writer)
                .build());
        // 트랜젝션 전에 지연 SQL 저장소 -> DB로 전송 (이미지 파일 저장을 위해서)
        boardRepository.flush();

        // 이미지 리스트에 데이터가 있다면 저장 (미리 INSERT한 Board를 호출해서 연관관계 저장)
        if (imageList.size() != 0) {
            for (BoardImage boardImage : imageList) {
                boardImageRepository.save(new BoardImage(
                        boardImage.getUploadFileName(), boardImage.getStoreFileName(), boardImage.getFilePath(), saveBoard));
            }
        }
    }

    /*
    * 상세 보기
    * */
    @Override
    public CommunityResponseDto findDetailView(Long boardId, HttpServletRequest request, HttpServletResponse response) {

        Cookie viewCookie = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(boardId + "_community")) {
                    log.info("COOKIE EXIST={}", cookies[i].getValue());
                    viewCookie = cookies[i];
                }
            }
        } else {
            log.info("COOKIE IS NOT EXIST!");
        }

        if (viewCookie == null) {
            Cookie newCookie = new Cookie(boardId + "_community", "boardView");
            response.addCookie(newCookie);
            boardRepository.addViewCount(boardId);
        } else {
            log.info("COOKIE IS EXIST");
            log.info("EXIST COOKIE={}", viewCookie.getValue());
        }

        return boardRepository.findDetailView(boardId);
    }

    /* 조회수 */
    @Override
    public int boardCount(Long boardId) {
        return boardRepository.boardCount(boardId);
    }

    /* 좋아요 추가 */
    @Override
    @Transactional
    public void insertLike(Long boardId, BasicUser basicUser) {

        CommunityBoard communityBoard = boardRepository.findById(boardId).orElseThrow();

        communityLikeRepository.save(new CommunityLike(communityBoard, basicUser));
    }

    /* 좋아요 삭제 */
    @Override
    public void deleteLike(Long boardId, Long memberId) {
        communityLikeRepository.deleteLike(boardId, memberId);
    }

    /* 좋아요 수 */
    @Override
    public int countLike(Long boardId, Long memberId) {
        return communityLikeRepository.countLike(boardId, memberId);
    }

    @Override
    @Transactional
    public Slice<CommunityResponseDto> findAllCommunityBoard(Pageable pageable) {
        return boardRepository.findAllCommunityList(pageable);
    }

    @Override
    public Slice<CommunityResponseDto> findByCategory(Pageable pageable, Category category) {
        return boardRepository.findByCategory(pageable, category);
    }
}

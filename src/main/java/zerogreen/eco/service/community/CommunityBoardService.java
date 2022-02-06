package zerogreen.eco.service.community;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Modifying;
import zerogreen.eco.dto.community.CommunityRequestDto;
import zerogreen.eco.dto.community.CommunityResponseDto;
import zerogreen.eco.entity.community.BoardImage;
import zerogreen.eco.entity.community.Category;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public interface CommunityBoardService {

    /* Community */
    Long boardRegister(CommunityRequestDto dto, Member writer, List<BoardImage> imageList);

    Slice<CommunityResponseDto> findAllCommunityBoard(Pageable pageable);
    Slice<CommunityResponseDto> findByCategory(Pageable pageable, Category category);

    CommunityResponseDto findDetailView(Long boardId, HttpServletRequest request, HttpServletResponse response);

    int boardCount(Long boardId);

    /* Like */
    int countLike(Long boardId, Long memberId);

    void insertLike(Long boardId, BasicUser basicUser);

    void deleteLike(Long boardId, Long memberId);

    int countLikeByBoard(Long boardId);

}

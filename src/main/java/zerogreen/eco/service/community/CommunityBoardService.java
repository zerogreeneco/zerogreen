package zerogreen.eco.service.community;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Modifying;
import zerogreen.eco.dto.community.CommunityRequestDto;
import zerogreen.eco.dto.community.CommunityResponseDto;
import zerogreen.eco.entity.community.BoardImage;
import zerogreen.eco.entity.community.Category;
import zerogreen.eco.entity.userentity.Member;

import java.util.List;

public interface CommunityBoardService {

    void boardRegister(CommunityRequestDto dto, Member writer, List<BoardImage> imageList);

    Slice<CommunityResponseDto> findAllCommunityBoard(Pageable pageable);
    Slice<CommunityResponseDto> findByCategory(Pageable pageable, Category category);

    CommunityResponseDto findDetailView(Long boardId);

    int boardCount(Long boardId);
}

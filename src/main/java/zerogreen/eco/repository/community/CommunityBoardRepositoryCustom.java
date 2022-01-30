package zerogreen.eco.repository.community;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import zerogreen.eco.dto.community.CommunityResponseDto;

public interface CommunityBoardRepositoryCustom {

    CommunityResponseDto findDetailBoard(Long boardId);

    Slice<CommunityResponseDto> findAllCommunityList(Pageable pageable);
}

package zerogreen.eco.repository.community;

import zerogreen.eco.dto.community.CommunityResponseDto;

public interface CommunityBoardRepositoryCustom {

    CommunityResponseDto findDetailBoard(Long boardId);
}

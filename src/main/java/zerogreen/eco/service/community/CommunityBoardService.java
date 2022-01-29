package zerogreen.eco.service.community;

import zerogreen.eco.dto.community.CommunityRequestDto;
import zerogreen.eco.entity.community.BoardImage;
import zerogreen.eco.entity.userentity.Member;

import java.util.List;

public interface CommunityBoardService {

    void boardRegister(CommunityRequestDto dto, Member writer, List<BoardImage> imageList);
}

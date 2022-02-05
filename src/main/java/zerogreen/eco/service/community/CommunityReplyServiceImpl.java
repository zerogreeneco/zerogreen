package zerogreen.eco.service.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.dto.community.CommunityReplyDto;
import zerogreen.eco.entity.community.BoardReply;
import zerogreen.eco.entity.community.CommunityBoard;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.repository.community.BoardReplyRepository;
import zerogreen.eco.repository.community.CommunityBoardRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityReplyServiceImpl implements CommunityReplyService{
    private final BoardReplyRepository boardReplyRepository;
    private final CommunityBoardRepository communityBoardRepository;

    @Override
    @Transactional
    public void replySave(String text, Long boardId, BasicUser basicUser) {
        CommunityBoard communityBoard = communityBoardRepository.findById(boardId).orElseThrow();

        boardReplyRepository.save(new BoardReply(text, basicUser, communityBoard));
    }

    @Override
    @Transactional
    public void modifyReply(Long replyId, String text) {

        BoardReply boardReply = boardReplyRepository.findById(replyId).orElseThrow();
        boardReply.changeText(text);
    }

    @Override
    @Transactional
    public void deleteReply(Long replyId) {
        boardReplyRepository.deleteById(replyId);
    }

    @Override
    public List<CommunityReplyDto> findReplyByBoardId(Long boardId) {
        List<BoardReply> boardRepliesByBoardId = boardReplyRepository.findBoardRepliesByBoardId(boardId);

        return boardRepliesByBoardId.stream().map(CommunityReplyDto::new).collect(Collectors.toList());
    }
}


package zerogreen.eco.service.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.dto.community.CommunityReplyDto;
import zerogreen.eco.entity.community.BoardReply;
import zerogreen.eco.entity.community.CommunityBoard;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.Member;
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

    /*
    * 댓글 저장
    * */
    @Override
    @Transactional
    public void replySave(String text, Long boardId, BasicUser basicUser) {
        CommunityBoard communityBoard = communityBoardRepository.findById(boardId).orElseThrow();
        boardReplyRepository.save(new BoardReply(text, basicUser, communityBoard));
    }

    /*
    * 대댓글 저장
    * */
    @Override
    @Transactional
    public void nestedReplySave(String text, Long boardId, BasicUser basicUser, Long replyId) {
        CommunityBoard communityBoard = communityBoardRepository.findById(boardId).orElseThrow();
        log.info("FIND BOARD>>>>>>");
        BoardReply parentReply = boardReplyRepository.findById(replyId).orElseThrow();
        log.info("FIND PARENT");
        BoardReply childReply = new BoardReply(text, basicUser, communityBoard);
        log.info("CHILD INSTANCE");
        boardReplyRepository.save(childReply);
        log.info("CHILD SAVE");
        parentReply.addNestedReply(childReply);
        log.info("CHILD ID={}",childReply.getId());
        log.info("NEST REPLY SERVICE END>>>>>>>>>");
        boardReplyRepository.flush();
    }

    /*
    * 댓글 수정
    * */
    @Override
    @Transactional
    public void modifyReply(Long replyId, String text) {

        BoardReply boardReply = boardReplyRepository.findById(replyId).orElseThrow();
        boardReply.changeText(text);
    }

    /*
    * 댓글 삭제
    * */
    @Override
    @Transactional
    public void deleteReply(Long replyId) {
        boardReplyRepository.deleteById(replyId);
    }

    /*
    * 댓글 리스트
    * */
    @Override
    public List<CommunityReplyDto> findReplyByBoardId(Long boardId) {
         return boardReplyRepository.findBoardRepliesByBoardId(boardId)
                .stream().map(CommunityReplyDto::new).collect(Collectors.toList());
    }
}

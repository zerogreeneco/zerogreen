package zerogreen.eco.service.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.dto.community.CommunityReplyDto;
import zerogreen.eco.entity.community.BoardNestedReply;
import zerogreen.eco.entity.community.BoardReply;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.repository.community.BoardReplyRepository;
import zerogreen.eco.repository.community.CommunityNestedReplyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityNestedReplyServiceImpl implements CommunityNestedReplyService {

    private final CommunityNestedReplyRepository nestedReplyRepository;
    private final BoardReplyRepository replyRepository;

    @Override
    @Transactional
    public void nestedReplySave(Long replyId, BasicUser basicUser, String text) {

        BoardReply boardReply = replyRepository.findById(replyId).orElseThrow();

        nestedReplyRepository.save(new BoardNestedReply(text, boardReply, basicUser));
    }

    @Override
    public List<CommunityReplyDto> findNestedReplyByReplyId(Long replyId) {

        return nestedReplyRepository.findNestedReplyByReplyId(replyId).stream()
                .map(CommunityReplyDto::new).collect(Collectors.toList());
    }
}

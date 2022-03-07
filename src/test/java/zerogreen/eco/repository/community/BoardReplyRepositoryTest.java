package zerogreen.eco.repository.community;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zerogreen.eco.entity.community.BoardReply;
import zerogreen.eco.entity.community.Category;
import zerogreen.eco.entity.community.CommunityBoard;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.repository.user.MemberRepository;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardReplyRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CommunityBoardRepository communityBoardRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private BoardReplyRepository boardReplyRepository;

//    @Test
//    public void 댓글_리스트() {
//
//        Member findMember = memberRepository.findById(47L).get();
//        CommunityBoard findBoard = communityBoardRepository.findById(2003L).get();
//        for (int i = 0; i < 3; i++) {
//            boardReplyRepository.save(new BoardReply("parent" + i, (BasicUser) findMember, findBoard));
//        }
//        entityManager.flush();
//        BoardReply findReply = boardReplyRepository.findById(1L).get();
//
//        for (int i = 0; i < 5; i++) {
//            findReply.addNestedReply(boardReplyRepository.save(new BoardReply("child" + i, (BasicUser) findMember, findBoard)));
//        }
//        entityManager.flush();
//        entityManager.clear();
//
//
//        List<BoardReply> boardRepliesByBoardId = boardReplyRepository.findBoardRepliesByBoardId(1L);
//        for (BoardReply boardReply : boardRepliesByBoardId) {
//            System.out.println("boardReply = " + boardReply);
//        }
//    }
}
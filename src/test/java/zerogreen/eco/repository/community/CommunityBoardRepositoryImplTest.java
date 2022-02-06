package zerogreen.eco.repository.community;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zerogreen.eco.entity.community.Category;
import zerogreen.eco.entity.community.CommunityBoard;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.entity.userentity.VegetarianGrade;
import zerogreen.eco.repository.user.MemberRepository;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommunityBoardRepositoryImplTest {

    @Autowired
    private CommunityBoardRepository communityBoardRepository;

    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private EntityManager em;

    @Test
    private void 상세보기_테스트() {

        Member member = new Member("test", "tester", "01022223333", "1",
                UserRole.USER, VegetarianGrade.LACTO);
        em.persist(member);
        em.flush();
        em.clear();

        Member member1 = memberRepository.findById(1L).get();

        CommunityBoard communityBoard = new CommunityBoard("TEXT", member1, Category.PLOGGING);
        
        em.persist(communityBoard);
        em.flush();
        em.clear();

        CommunityBoard communityBoard1 = communityBoardRepository.findById(1L).get();

        communityBoardRepository.findDetailView(communityBoard1.getId());

        System.out.println("communityBoard1 = " + communityBoard1);

    }
}
package zerogreen.eco.repository.community;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.dto.community.CommunityResponseDto;
import zerogreen.eco.entity.community.Category;
import zerogreen.eco.entity.community.CommunityBoard;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.entity.userentity.VegetarianGrade;
import zerogreen.eco.repository.user.MemberRepository;

import javax.persistence.EntityManager;

import java.time.Duration;
import java.time.Instant;

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

        CommunityBoard communityBoard = new CommunityBoard("TEXT", member1, Category.PLOGGING, true );
        
        em.persist(communityBoard);
        em.flush();
        em.clear();

        CommunityBoard communityBoard1 = communityBoardRepository.findById(1L).get();

        communityBoardRepository.findDetailView(communityBoard1.getId());

        System.out.println("communityBoard1 = " + communityBoard1);

    }

    @Test
    @Transactional
    public void 리스트_테스트() {
        Member member = new Member("test", "김그네", "01022223333", "1",
                UserRole.USER, VegetarianGrade.VEGAN);

        em.flush();
        em.clear();

        Member member1 = memberRepository.findByUsername("test").get();

        for (int i = 0; i < 1000; i++) {
            communityBoardRepository.save(new CommunityBoard("TEST TEXT", member1, Category.PLOGGING, true));
        }

        em.flush();
        em.clear();

        Pageable pageable = PageRequest.of(0, 1000);
        Instant start = Instant.now();
        communityBoardRepository.findAllCommunityList(pageable);
        Instant close = Instant.now();
        Long timeElapsed = Duration.between(start, close).getSeconds();
        System.out.println("timeElapsed = " + timeElapsed);
    }

}
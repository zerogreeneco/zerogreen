package zerogreen.eco.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.entity.userentity.VegetarianGrade;
import zerogreen.eco.repository.MemberRepository;
import zerogreen.eco.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceImplTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Test
    public void 회원정보수정() {
        Member member = new Member("test", "test", "0001111111", "1234", UserRole.USER, VegetarianGrade.LACTO);
        Long memberId = memberService.save(member);


        userRepository.flush();

        Optional<Member> findMember = memberRepository.findById(memberId);
        System.out.println("findMember = " + findMember);
        assertThat(member.getNickname()).isEqualTo(findMember.get().getNickname());

        findMember.get().setNickname("NEW");
        findMember.get().setPhoneNumber("222233333");

        memberService.memberUpdate(findMember.get().getId(), findMember.get());

        memberRepository.flush();

        Member member1 = memberRepository.findById(memberId).get();
        System.out.println("member1 = " + member1);;
    }
}
package zerogreen.eco.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.entity.userentity.VegetarianGrade;
import zerogreen.eco.repository.user.MemberRepository;
import zerogreen.eco.repository.user.BasicUserRepository;
import zerogreen.eco.service.user.MemberService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberServiceImplTest {

    @Autowired
    BasicUserRepository userRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void 회원정보수정() {
        Member member = new Member("test", "test", "0001111111", "1234", UserRole.USER, VegetarianGrade.LACTO);
        Long memberId = memberService.save(member);

        userRepository.flush();

        Optional<Member> findMember = memberRepository.findById(memberId);
        System.out.println("findMember = " + findMember.get().getPassword());
        assertThat(member.getNickname()).isEqualTo(findMember.get().getNickname());

        findMember.get().setNickname("NEW");
        findMember.get().setPhoneNumber("222233333");

        memberService.memberUpdate(findMember.get().getId(), findMember.get());

        memberRepository.flush();

        Member member1 = memberRepository.findById(memberId).get();
        System.out.println("member1 = " + member1);;
    }
}
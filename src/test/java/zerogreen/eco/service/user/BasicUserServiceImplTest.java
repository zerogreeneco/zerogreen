package zerogreen.eco.service.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.dto.member.FindMemberDto;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.entity.userentity.VegetarianGrade;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional(readOnly = true)
class BasicUserServiceImplTest {

    @Autowired
    private BasicUserService basicUserService;

    @Autowired
    private MemberService memberService;

//    @Test
//    public void 회원정보_찾기() {
//        Long saveMember = memberService.save(new Member("test2", "tester", "01022223333", "1",
//                UserRole.USER, VegetarianGrade.LACTO));
//
//        Member member = memberService.findById(saveMember).get();
//
////        BasicUser findMember = basicUserService.findByUsernameAndPhoneNumber("test2", "01022223333");
//
//        assertThat(member.getPassword()).isEqualTo(findMember.getPassword());
//    }

    @Test
    public void 연락처_회원조회() {
        Long saveMember = memberService.save(new Member("test2", "tester", "01099990000", "1",
                UserRole.USER, VegetarianGrade.LACTO));

        Member member = memberService.findById(saveMember).get();
        System.out.println("member.getPhoneNumber() = " + member.getPhoneNumber());
        long count = basicUserService.countByPhoneNumber(member.getPhoneNumber());
        System.out.println("count = " + count);
        FindMemberDto byPhoneNumber = basicUserService.findByPhoneNumber(member.getPhoneNumber());
        assertThat(count).isEqualTo(1);
        assertThat(byPhoneNumber.getUsername()).isEqualTo(member.getUsername());
    }
}
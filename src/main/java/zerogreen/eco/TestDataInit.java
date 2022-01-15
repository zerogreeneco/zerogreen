package zerogreen.eco;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.entity.userentity.VegetarianGrade;
import zerogreen.eco.service.user.MemberService;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberService memberService;

    @PostConstruct
    public void init() {
        memberService.save(new Member("test", "tester", "01022223333", "1", UserRole.USER, VegetarianGrade.LACTO));
    }

}

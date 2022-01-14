package zerogreen.eco.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.entity.userentity.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
public class MemberRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    public void 회원저장() {

        Member member = new Member("test", "test2", "000000", "135", UserRole.USER, VegetarianGrade.LACTO);

        userRepository.save(member);
        Optional<User> findMember = userRepository.findById(member.getId());
        assertThat(member).isEqualTo(findMember.get());
    }

}

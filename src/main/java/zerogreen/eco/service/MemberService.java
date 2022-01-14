package zerogreen.eco.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final UserRepository userRepository;

    public Long save(Member member) {

        return userRepository.save(new Member(member.getUsername(), member.getNickname(), member.getPhoneNumber(), member.getPassword(),
                UserRole.USER, member.getVegetarianGrade())).getId();
    }

}

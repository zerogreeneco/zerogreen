package zerogreen.eco.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.repository.MemberRepository;
import zerogreen.eco.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Long save(Member member) {
        return memberRepository.save(new Member(member.getUsername(), member.getNickname(),
                member.getPhoneNumber(), member.getPassword(), UserRole.USER, member.getVegetarianGrade()) )
                .getId();
    }

    @Transactional
    @Override
    public void memberUpdate(Long id, Member member) {
        Optional<Member> updateMember = memberRepository.findById(id);

        BasicUser user = updateMember.get();
        user.setNickname(member.getNickname());
        user.setPhoneNumber(member.getPhoneNumber());
        member.setVegetarianGrade(member.getVegetarianGrade());
    }
}

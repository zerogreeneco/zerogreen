package zerogreen.eco.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Long save(Member member) {
        String encPassword = passwordEncoder.encode(member.getPassword());

        return memberRepository.save(new Member(member.getUsername(), member.getNickname(),
                member.getPhoneNumber(), encPassword, UserRole.USER, member.getVegetarianGrade()) )
                .getId();
    }

    @Transactional
    @Override
    public void memberUpdate(Long id, Member member) {
        Optional<Member> findeMember = memberRepository.findById(id);

        Member updateMember  = findeMember.get();
        updateMember.setNickname(member.getNickname());
        updateMember.setPhoneNumber(member.getPhoneNumber());
        updateMember.setVegetarianGrade(member.getVegetarianGrade());
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }
}

package zerogreen.eco.service.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.dto.member.MemberAuthDto;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.repository.user.BasicUserRepository;
import zerogreen.eco.repository.user.MemberRepository;
import zerogreen.eco.service.mail.MailService;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{

    private final BasicUserRepository basicUserRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    /*
    * 테스트 데이터용
    * */
    @Transactional
    @Override
    public Long save(Member member) {
        String encPassword = passwordEncoder.encode(member.getPassword());

        return memberRepository.save(new Member(member.getUsername(), member.getNickname(),
                member.getPhoneNumber(), encPassword, UserRole.USER, member.getVegetarianGrade()) )
                .getId();
    }

    /*
    * 회원가입
    * */
    @Transactional
    @Override
    public Long saveV2(Member member) {
        String encPassword = passwordEncoder.encode(member.getPassword());

        return memberRepository.save(new Member(member.getUsername(), member.getNickname(), member.getPhoneNumber(),
                        encPassword, UserRole.USER, false, member.getVegetarianGrade()) )
                .getId();
    }

    /*
    * 회원 인증 상태 변경 : false -> true
    * */
    @Transactional
    @Override
    public void changeAuthState(Long id) {
        Member joinMember = memberRepository.findById(id).orElseGet(null);

        joinMember.setAuthState(true);
    }

    @Transactional
    @Override
    public void memberUpdate(Long id, Member member) {
        Member updateMember  = memberRepository.findById(id).orElseThrow();

        updateMember.setNickname(member.getNickname());
        updateMember.setPhoneNumber(member.getPhoneNumber());
        updateMember.setVegetarianGrade(member.getVegetarianGrade());
    }

    @Transactional
    @Override
    public void kakaoMemberUpdate(Long id, Member member) {
        Member updateMember  = memberRepository.findById(id).orElseThrow();

        updateMember.setPhoneNumber(member.getPhoneNumber());
        updateMember.setVegetarianGrade(member.getVegetarianGrade());
        updateMember.setSocialType("KAKAO");
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public MemberAuthDto findAuthMember(String username) {
        return basicUserRepository.findByAuthUsername(username);
    }

}

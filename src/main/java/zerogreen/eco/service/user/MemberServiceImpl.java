package zerogreen.eco.service.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.dto.member.MemberAuthDto;
import zerogreen.eco.dto.member.MemberUpdateDto;
import zerogreen.eco.dto.member.PasswordUpdateDto;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.repository.user.BasicUserRepository;
import zerogreen.eco.repository.user.MemberRepository;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{

    private final BasicUserRepository basicUserRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


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

    @Override
    public MemberUpdateDto toMemberUpdateDto(String username, MemberUpdateDto memberUpdateDto) {
        Member member = memberRepository.findByUsername(username).orElseThrow();

        return new MemberUpdateDto(member.getUsername(), member.getNickname(), member.getPhoneNumber(), member.getVegetarianGrade());
    }

    /*
    * 회원 정보 수정
    * */
    @Transactional
    @Override
    public void memberUpdate(Long id, MemberUpdateDto memberUpdateResponse) {
        Member updateMember  = memberRepository.findById(id).orElseThrow();

        updateMember.setNickname(memberUpdateResponse.getNickname());
        updateMember.setPhoneNumber(memberUpdateResponse.getPhoneNumber());
        updateMember.setVegetarianGrade(memberUpdateResponse.getVegetarianGrade());
    }

    /*
    * 카카오 회원가입 추가 정보
    * */
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

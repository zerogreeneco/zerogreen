package zerogreen.eco.service.user;

import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.dto.member.MemberAuthDto;
import zerogreen.eco.dto.member.MemberUpdateDto;
import zerogreen.eco.dto.member.PasswordUpdateDto;
import zerogreen.eco.entity.userentity.Member;

import java.util.Optional;

public interface MemberService {

    Long save(Member member);
    Long saveV2(Member member);
    void changeAuthState(Long id);
    void memberUpdate(Long id, MemberUpdateDto memberUpdateDto);

    MemberUpdateDto detailMemberInfo(String username);
    MemberUpdateDto toMemberUpdateDto(String username, MemberUpdateDto memberUpdateDto);
    void kakaoMemberUpdate(Long id, Member member);
    Optional<Member> findById(Long id);
    Long findAuthMember(String username);

    // 닉네임 중복체크
    Integer countByNickname(String nickname);

}

package zerogreen.eco.service.user;

import zerogreen.eco.dto.MemberAuthDto;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.Member;

import java.util.Optional;

public interface MemberService {

    public Long save(Member member);

    public Long saveV2(Member member);

    public void changeAuthState(Long id);

    public void memberUpdate(Long id, Member member);

    public Optional<Member> findById(Long id);

    public MemberAuthDto findAuthMember(String username);


}

package zerogreen.eco.repository.user;

import zerogreen.eco.dto.MemberAuthDto;

public interface BasicUserRepositoryCustom {

    public MemberAuthDto findAuthMember(Long id);
}

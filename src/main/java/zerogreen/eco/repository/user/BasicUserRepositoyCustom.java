package zerogreen.eco.repository.user;

import zerogreen.eco.dto.MemberAuthDto;

public interface BasicUserRepositoyCustom {
    public MemberAuthDto findAuthMember(Long id);
}

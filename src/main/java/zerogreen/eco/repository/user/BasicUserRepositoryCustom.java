package zerogreen.eco.repository.user;

import zerogreen.eco.dto.member.MemberAuthDto;

public interface BasicUserRepositoryCustom {

    public MemberAuthDto findByAuthUsername(String username);
}

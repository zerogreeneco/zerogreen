package zerogreen.eco.service;

import zerogreen.eco.entity.userentity.Member;

import java.util.Optional;

public interface MemberService {

    public Long save(Member member);

    public void memberUpdate(Long id, Member member);

    public Optional<Member> findById(Long id);
}

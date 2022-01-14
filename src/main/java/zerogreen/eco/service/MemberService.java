package zerogreen.eco.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.repository.UserRepository;

public interface MemberService {

    public Long save(Member member);
}

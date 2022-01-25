package zerogreen.eco.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zerogreen.eco.dto.member.MemberAuthDto;
import zerogreen.eco.dto.store.NonApprovalStoreDto;
import zerogreen.eco.entity.userentity.BasicUser;

import java.util.List;

public interface BasicUserRepositoryCustom {

    MemberAuthDto findByAuthUsername(String username);

    Page<NonApprovalStoreDto> findByUnApprovalStore(Pageable pageable);

    void changeUserRole(List<Long> memberId);
}

package zerogreen.eco.repository.user;

import zerogreen.eco.dto.member.MemberAuthDto;
import zerogreen.eco.dto.store.NonApprovalStoreDto;

import java.util.List;

public interface BasicUserRepositoryCustom {

    public MemberAuthDto findByAuthUsername(String username);

    public List<NonApprovalStoreDto> findByUnApprovalStore();
}

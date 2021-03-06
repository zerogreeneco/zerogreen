package zerogreen.eco.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import zerogreen.eco.dto.member.MemberAuthDto;
import zerogreen.eco.dto.search.SearchType;
import zerogreen.eco.dto.store.NonApprovalStoreDto;
import zerogreen.eco.entity.userentity.BasicUser;

import java.util.List;

public interface BasicUserRepositoryCustom {

    Long findByAuthUsername(String username);

    Page<NonApprovalStoreDto> findByUnApprovalStore(Pageable pageable);
    Page<NonApprovalStoreDto> findByUnApprovalStoreSearch(Pageable pageable, String searchType, String searchText);

    Page<NonApprovalStoreDto> searchAndPaging(NonApprovalStoreDto condition, Pageable pageable);

    @Modifying
    void changeUserRole(List<Long> memberId);
}

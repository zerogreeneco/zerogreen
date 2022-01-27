package zerogreen.eco.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zerogreen.eco.dto.member.FindMemberDto;
import zerogreen.eco.dto.member.PasswordUpdateDto;
import zerogreen.eco.dto.store.NonApprovalStoreDto;

import java.util.List;

public interface BasicUserService {

    Long adminSave();

    Page<NonApprovalStoreDto> findByNonApprovalStore(Pageable pageable);

    FindMemberDto findByPhoneNumber(String phoneNumber);
    FindMemberDto findByUsernameAndPhoneNumber(String username, String phoneNumber);

    Page<NonApprovalStoreDto> nonApprovalStoreSearch(NonApprovalStoreDto SearchCond, Pageable pageable);

    void passwordChange(Long id, PasswordUpdateDto passwordUpdateDto);

    long countByPhoneNumber(String phoneNumber);
    long countByUsername(String username);

    void changeStoreUserRole(List<Long> memberId);

    void memberDelete(Long id);

}

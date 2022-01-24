package zerogreen.eco.service.user;

import zerogreen.eco.dto.member.FindMemberDto;
import zerogreen.eco.dto.member.MemberUpdateDto;
import zerogreen.eco.dto.member.PasswordUpdateDto;
import zerogreen.eco.dto.store.NonApprovalStoreDto;

import java.util.List;
import java.util.Optional;

public interface BasicUserService {

    Long adminSave();

    List<NonApprovalStoreDto> findByNonApprovalStore();

    FindMemberDto findByPhoneNumber(String phoneNumber);
    FindMemberDto findByUsernameAndPhoneNumber(String username, String phoneNumber);

    void passwordChange(Long id, PasswordUpdateDto passwordUpdateDto);

    long countByPhoneNumber(String phoneNumber);
    long countByUsername(String username);

    void changeStoreUserRole(List<Long> memberId);
}

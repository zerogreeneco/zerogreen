package zerogreen.eco.repository.user;

import zerogreen.eco.dto.store.NonApprovalStoreDto;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.entity.userentity.UserRole;

import java.util.List;

public interface StoreMemberRepositoryCustom {
    List<NonApprovalStoreDto> findByApprovalStore(UserRole userRole);

    //get Store DB in page Detail
    //StoreDto getStoreById(Long sno);

    }

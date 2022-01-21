package zerogreen.eco.service.user;

import zerogreen.eco.dto.store.NonApprovalStoreDto;
import zerogreen.eco.entity.userentity.BasicUser;

import java.util.List;

public interface BasicUserService {

    Long adminSave();

    List<NonApprovalStoreDto> findByNonApprovalStore();
}
